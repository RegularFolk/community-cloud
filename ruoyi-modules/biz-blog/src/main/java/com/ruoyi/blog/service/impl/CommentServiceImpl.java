package com.ruoyi.blog.service.impl;

import com.ruoyi.blog.config.BlogConstants;
import com.ruoyi.blog.domain.Blog;
import com.ruoyi.blog.domain.BlogComment;
import com.ruoyi.blog.domain.dto.CommentLikeDto;
import com.ruoyi.blog.domain.dto.GetCommentDto;
import com.ruoyi.blog.domain.dto.PostCommentDto;
import com.ruoyi.blog.domain.vo.BlogCommentVo;
import com.ruoyi.blog.enums.CommentOrderEnum;
import com.ruoyi.blog.mapper.BlogCommentMapper;
import com.ruoyi.blog.mapper.BlogMapper;
import com.ruoyi.blog.mapper.CommentLikedMapper;
import com.ruoyi.blog.service.CommentService;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.mq.callBack.DefaultCallBack;
import com.ruoyi.common.mq.constants.MqTopicConstants;
import com.ruoyi.common.mq.domain.BlogMessage;
import com.ruoyi.common.mq.domain.CommentMessage;
import com.ruoyi.common.mq.enums.OperateType;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteUserService;
import com.ruoyi.system.api.domain.SysUser;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    BlogMapper blogMapper;

    @Resource
    BlogCommentMapper blogCommentMapper;

    @Resource
    CommentLikedMapper commentLikedMapper;

    @Resource
    RemoteUserService remoteUserService;

    @Resource
    private RocketMQTemplate rocketmqTemplate;

    private static final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);


    @Override
    public BlogCommentVo getComments(GetCommentDto dto) {
        Long blogId = dto.getBlogId();
        Long start = dto.getStart();
        boolean refreshFlag = dto.getRefreshFlag();
        String commentOrder = dto.getOrder();
        CommentOrderEnum orderEnum = CommentOrderEnum.getEnum(commentOrder);
        // 获取评论总数
        Blog blog = new Blog();
        blog.setId(blogId);
        List<Blog> blogList = blogMapper.getArticleList(blog, null, null, null);
        long commentTotal = blogList.get(0).getCommentCnt();


        int parentCnt = blogCommentMapper.getParentCommentCnt(blogId);
        BlogCommentVo blogCommentVo = new BlogCommentVo();

        if (parentCnt == 0 || (!refreshFlag && parentCnt <= start)) {
            blogCommentVo.setHasMore(false);
            blogCommentVo.setCommentCnt(0L);
            blogCommentVo.setComments(new ArrayList<>());
            blogCommentVo.setCommentTotal((long) parentCnt);
            return blogCommentVo;
        }

        List<BlogComment> parentCommentList;
        if (!refreshFlag) {
            parentCommentList = blogCommentMapper.getParentCommentPartly(blogId, start, (long) BlogConstants.COMMENT_STEP, orderEnum.getOrder());
        } else {
            parentCommentList = blogCommentMapper.getParentCommentPartly(blogId, 0L, start, orderEnum.getOrder());
        }
        List<Long> parentCommentIds = parentCommentList.stream().map(BlogComment::getId).collect(Collectors.toList());
        List<BlogComment> subCommentList = blogCommentMapper.getSubComment(blogId, parentCommentIds);
        Map<Long, List<BlogComment>> subCommentMap = subCommentList.stream().collect(Collectors.groupingBy(BlogComment::getParentId));


        // 获取评论的用户名、头像信息
        List<BlogComment> allCommentList = new ArrayList<>();
        allCommentList.addAll(parentCommentList);
        allCommentList.addAll(subCommentList);
        List<Long> senderIds = allCommentList.stream().map(BlogComment::getSenderId).collect(Collectors.toList());
        List<Long> receiverIds = allCommentList.stream().map(BlogComment::getReceiverId).collect(Collectors.toList());
        senderIds.addAll(receiverIds);
        senderIds = senderIds.stream().filter(id -> !id.equals(-1L)).distinct().collect(Collectors.toList());
        R<List<SysUser>> userInfoRes = remoteUserService.getInfoByIds(senderIds, SecurityConstants.INNER);
        if (R.FAIL == userInfoRes.getCode()) {
            throw new ServiceException("获取发送者或接受者信息失败!");
        }
        Map<Long, List<SysUser>> userInfoMap = userInfoRes.getData().stream().collect(Collectors.groupingBy(SysUser::getUserId));

        // 获取是否已点赞信息
        Long userId = SecurityUtils.getUserId();
        List<Long> commentIdList = allCommentList.stream().map(BlogComment::getId).collect(Collectors.toList());
        List<Long> likedCommentIdList = commentLikedMapper.getLikedIds(userId, commentIdList);
        Set<Long> commentIdSet = new HashSet<>(likedCommentIdList);


        // 打包数据
        blogCommentVo.setCommentCnt((long) allCommentList.size());
        // if s+2 >= total no more , so has more = total > s + step - 1, total >= s + step
        blogCommentVo.setHasMore(refreshFlag ? parentCnt >= start : parentCnt - start >= BlogConstants.COMMENT_STEP);
        // 获取 parentCommentUnit
        List<BlogCommentVo.CommentUnit> parentCommentUnitList = parentCommentList.stream().map(pc -> {
            BlogCommentVo.CommentUnit commentUnit = new BlogCommentVo.CommentUnit();

            SysUser sender = userInfoMap.get(pc.getSenderId()).get(0);
            commentUnit.setLiked(commentIdSet.contains(pc.getId()));
            commentUnit.packFromBlogComment(pc, sender, null);

            // 获取 subCommentUnit
            List<BlogComment> subComments = subCommentMap.get(pc.getId());
            if (!CollectionUtils.isEmpty(subComments)) {
                List<BlogCommentVo.CommentUnit> subCommentUnitList = subComments.stream().map(sc -> {
                    BlogCommentVo.CommentUnit subCommentUnit = new BlogCommentVo.CommentUnit();

                    SysUser subSender = userInfoMap.get(sc.getSenderId()).get(0);
                    SysUser subReceiver = userInfoMap.get(sc.getReceiverId()).get(0);
                    subCommentUnit.packFromBlogComment(sc, subSender, subReceiver);
                    subCommentUnit.setLiked(commentIdSet.contains(sc.getId()));

                    return subCommentUnit;
                }).sorted((a, b) -> {
                    // 根据commentOrder对子评论进行排序
                    switch (orderEnum) {
                        case TIME_DESC:
                            return b.getSendTime().compareTo(a.getSendTime());
                        case LIKE_DESC:
                            return b.getLikeCnt().compareTo(a.getLikeCnt());
                        case TIME_ASC:
                        default:
                            return a.getSendTime().compareTo(b.getSendTime());
                    }
                }).collect(Collectors.toList());

                commentUnit.setSubComments(subCommentUnitList);
            }

            return commentUnit;
        }).collect(Collectors.toList());

        blogCommentVo.setComments(parentCommentUnitList);
        blogCommentVo.setCommentTotal(commentTotal);

        return blogCommentVo;
    }

    @Override
    public boolean postComment(PostCommentDto dto) {
        BlogComment comment = new BlogComment();
        Long senderId = SecurityUtils.getUserId();
        comment.setBlogId(dto.getBlogId());
        comment.setContent(dto.getContent());
        comment.setSenderId(senderId);
        if (dto.getReceiverId() != null) {
            comment.setReceiverId(dto.getReceiverId());
        }
        if (dto.getParentId() != null) {
            comment.setParentId(dto.getParentId());
        }

        int flag = blogCommentMapper.putComment(comment);

        if (flag > 0) {
            // 下游通知blog评论数更新

            BlogMessage message = new BlogMessage();
            message.setMessageId(dto.getBlogId());
            message.setBlogId(dto.getBlogId());
            message.setOperateType(OperateType.ADD.getType());
            message.setType(BlogMessage.MessageType.COMMENT.getType());
            rocketmqTemplate.asyncSendOrderly(
                    MqTopicConstants.BLOG_TOPIC,
                    message,
                    String.valueOf(message.getBlogId()),
                    new DefaultCallBack<>(this.getClass(), message)
            );
        }

        return flag > 0;
    }

    @Override
    public int commentLike(CommentLikeDto dto) {
        int flag = 0;
        Long userId = SecurityUtils.getUserId();
        Long commentId = dto.getCommentId();
        OperateType typeEnum = OperateType.getEnum(dto.getOperateType());

        switch (typeEnum) {
            case ADD:
                int cnt = commentLikedMapper.isLiked(userId, commentId);
                if (cnt == 0) {
                    flag = commentLikedMapper.insertLike(userId, commentId);
                }
                break;
            case CANCEL:
                flag = commentLikedMapper.deleteLike(userId, commentId);
                break;
            default:
                // unreachable
                break;
        }

        if (flag > 0) {
            // 下游通知评论点赞数更新
            CommentMessage message = new CommentMessage();
            message.setMessageId(commentId);
            message.setCommentId(commentId);
            message.setType(CommentMessage.MessageType.LIKE.getType());
            message.setOperateType(typeEnum.getType());
            rocketmqTemplate.asyncSendOrderly(
                    MqTopicConstants.COMMENT_TOPIC,
                    message,
                    String.valueOf(message.getMessageId()),
                    new DefaultCallBack<>(this.getClass(), message)
            );

        }

        return flag;
    }
}
