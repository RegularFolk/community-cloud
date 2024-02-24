package com.ruoyi.blog.service.impl;

import com.ruoyi.blog.config.BlogConstants;
import com.ruoyi.blog.domain.Blog;
import com.ruoyi.blog.domain.dto.BlogLikeDto;
import com.ruoyi.blog.domain.dto.PostBlogDto;
import com.ruoyi.blog.domain.vo.IndexBlogVo;
import com.ruoyi.blog.enums.BlogStatusEnum;
import com.ruoyi.blog.enums.BlogTypeEnum;
import com.ruoyi.blog.mapper.BlogLikedMapper;
import com.ruoyi.blog.mapper.BlogMapper;
import com.ruoyi.blog.service.BlogService;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.mq.callBack.DefaultCallBack;
import com.ruoyi.common.mq.constants.MqTopicConstants;
import com.ruoyi.common.mq.domain.BlogLikeMessage;
import com.ruoyi.common.mq.enums.OperateType;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteUserService;
import com.ruoyi.system.api.domain.SysUser;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    private BlogMapper blogMapper;

    @Resource
    private BlogLikedMapper blogLikedMapper;

    @Resource
    private RemoteUserService remoteUserService;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public List<IndexBlogVo> getRandomBlog() {
        List<Blog> blogList = blogMapper.getTempBlogList();
        List<IndexBlogVo> blogVoList = new ArrayList<>();
        if (CollectionUtils.isEmpty(blogList)) {
            return blogVoList;
        }
        List<Long> idList = blogList.stream().map(Blog::getAuthorId).collect(Collectors.toList());
        R<List<SysUser>> infoByIdsRes = remoteUserService.getInfoByIds(idList, SecurityConstants.INNER);
        List<SysUser> infoByIds = infoByIdsRes.getData();
        Map<Long, List<SysUser>> groupById = infoByIds.stream().collect(Collectors.groupingBy(SysUser::getUserId));

        Long userId = SecurityUtils.getUserId();

        blogVoList = blogList.stream().map(blog -> {
            IndexBlogVo blogVo = new IndexBlogVo();
            SysUser sysUser = groupById.get(blog.getAuthorId()).get(0);

            blogVo.setBlogId(blog.getId());
            blogVo.setAuthorFollowed(false); // 测试方法默认设置为未关注
            blogVo.setReleaseTime(blog.getReleaseTime());
            if (blog.getPreview().length() > BlogConstants.PREVIEW_LENGTH) {
                blogVo.setPreview(blog.getPreview().substring(0, BlogConstants.PREVIEW_LENGTH) + "······");
            } else {
                blogVo.setPreview(blog.getPreview());
            }
            blogVo.setLikeCnt(blog.getLikeCnt());
            blogVo.setViewCnt(blog.getViewCnt());
            blogVo.setCommentCnt(blog.getCommentCnt());
            blogVo.setSenderName(sysUser.getNickName());
            blogVo.setAvatar(sysUser.getAvatar());

            // 判断是否已点赞
            int liked = blogLikedMapper.isLiked(userId, blog.getId());
            blogVo.setLiked(liked > 0);

            return blogVo;
        }).collect(Collectors.toList());


        return blogVoList;
    }

    @Override
    public int blogLike(BlogLikeDto dto) {
        int flag = 0;
        Long userId = SecurityUtils.getUserId();
        Long blogId = dto.getBlogId();
        OperateType typeEnum = OperateType.getEnum(dto.getOperateType());

        switch (typeEnum) {
            case ADD:
                int cnt = blogLikedMapper.isLiked(userId, blogId);
                if (cnt == 0) {
                    flag = blogLikedMapper.insertLike(userId, blogId);
                }
                break;
            case CANCEL:
                flag = blogLikedMapper.deleteLike(userId, blogId);
                break;
            default:
                // unreachable
                break;
        }

        if (flag > 0) {
            // 通知下游改变点赞计数
            BlogLikeMessage message = new BlogLikeMessage();
            message.setOperateType(typeEnum.getType());
            message.setUserId(userId);
            message.setBlogId(blogId);
            message.setMessageId(blogId);
            rocketMQTemplate.asyncSendOrderly(
                    MqTopicConstants.LIKE_TOPIC,
                    message,
                    String.valueOf(message.getMessageId()),
                    new DefaultCallBack<>(this.getClass(), message)
            );
        }

        return flag;
    }

    @Override
    public long postBlog(PostBlogDto dto) {
        Long userId = SecurityUtils.getUserId();
        Blog blog = new Blog();
        blog.setAuthorId(userId);
        blog.setPreview(dto.getContent());
        blog.setStatus(BlogStatusEnum.PUBLISHED.getStatus());
        blog.setReleaseTime(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, new Date()));
        blog.setType(BlogTypeEnum.TWEET.getType());
        blog.setPicUrls(dto.getPicUrls());
        blogMapper.insertBlog(blog);
        return blog.getId();
    }


}
