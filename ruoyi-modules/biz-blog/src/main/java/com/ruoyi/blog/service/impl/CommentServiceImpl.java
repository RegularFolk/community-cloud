package com.ruoyi.blog.service.impl;

import com.ruoyi.blog.config.BlogConstants;
import com.ruoyi.blog.domain.BlogComment;
import com.ruoyi.blog.domain.dto.PostCommentDto;
import com.ruoyi.blog.domain.vo.BlogCommentVo;
import com.ruoyi.blog.mapper.BlogCommentMapper;
import com.ruoyi.blog.service.CommentService;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteUserService;
import com.ruoyi.system.api.domain.SysUser;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    BlogCommentMapper blogCommentMapper;

    @Resource
    RemoteUserService remoteUserService;


    @Override
    public BlogCommentVo getComments(Long blogId, Long start, Boolean refreshFlag) {
        int parentCnt = blogCommentMapper.getParentCommentCnt(blogId);
        BlogCommentVo blogCommentVo = new BlogCommentVo();

        if (parentCnt == 0 || (!refreshFlag && parentCnt <= start)) {
            blogCommentVo.setHasMore(false);
            blogCommentVo.setCommentCnt(0L);
            blogCommentVo.setComments(new ArrayList<>());
            return blogCommentVo;
        }

        List<BlogComment> parentCommentList;
        if (!refreshFlag) {
            parentCommentList = blogCommentMapper.getParentCommentPartly(blogId, start, (long) BlogConstants.COMMENT_STEP);
        } else {
            parentCommentList = blogCommentMapper.getParentCommentPartly(blogId, 0L, start + BlogConstants.COMMENT_STEP);
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

        // 打包数据
        blogCommentVo.setCommentCnt((long) allCommentList.size());
        blogCommentVo.setHasMore(parentCnt - start > BlogConstants.COMMENT_STEP);
        // 获取 parentCommentUnit
        List<BlogCommentVo.CommentUnit> parentCommentUnitList = parentCommentList.stream().map(pc -> {
            BlogCommentVo.CommentUnit commentUnit = new BlogCommentVo.CommentUnit();

            SysUser sender = userInfoMap.get(pc.getSenderId()).get(0);
            commentUnit.packFromBlogComment(pc, sender, null);

            // 获取 subCommentUnit
            List<BlogComment> subComments = subCommentMap.get(pc.getId());
            if (!CollectionUtils.isEmpty(subComments)) {
                List<BlogCommentVo.CommentUnit> subCommentUnitList = subComments.stream().map(sc -> {
                    BlogCommentVo.CommentUnit subCommentUnit = new BlogCommentVo.CommentUnit();

                    SysUser subSender = userInfoMap.get(sc.getSenderId()).get(0);
                    SysUser subReceiver = userInfoMap.get(sc.getReceiverId()).get(0);
                    subCommentUnit.packFromBlogComment(sc, subSender, subReceiver);

                    return subCommentUnit;
                }).sorted(Comparator.comparing(BlogCommentVo.CommentUnit::getSendTime)).collect(Collectors.toList());

                commentUnit.setSubComments(subCommentUnitList);
            }

            return commentUnit;
        }).sorted(Comparator.comparing(BlogCommentVo.CommentUnit::getSendTime)).collect(Collectors.toList());

        blogCommentVo.setComments(parentCommentUnitList);

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

        return flag > 0;
    }
}
