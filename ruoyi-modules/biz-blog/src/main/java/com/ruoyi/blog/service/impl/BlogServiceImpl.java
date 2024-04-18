package com.ruoyi.blog.service.impl;

import com.ruoyi.blog.config.BlogConstants;
import com.ruoyi.blog.domain.Blog;
import com.ruoyi.blog.domain.dto.BlogLikeDto;
import com.ruoyi.blog.domain.dto.BlogListDto;
import com.ruoyi.blog.domain.dto.PostBlogDto;
import com.ruoyi.blog.domain.vo.BlogDetailVo;
import com.ruoyi.blog.domain.vo.IndexBlogVo;
import com.ruoyi.blog.enums.BlogQueryModeEnum;
import com.ruoyi.blog.enums.BlogStatusEnum;
import com.ruoyi.common.mq.enums.BlogTypeEnum;
import com.ruoyi.blog.mapper.BlogLikedMapper;
import com.ruoyi.blog.mapper.BlogMapper;
import com.ruoyi.blog.service.ArticleService;
import com.ruoyi.blog.service.BlogService;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.IdDto;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.sql.SqlUtil;
import com.ruoyi.common.mq.callBack.DefaultCallBack;
import com.ruoyi.common.mq.constants.MqTopicConstants;
import com.ruoyi.common.mq.domain.BlogMessage;
import com.ruoyi.common.mq.domain.UserMessage;
import com.ruoyi.common.mq.enums.OperateType;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteUserService;
import com.ruoyi.system.api.domain.SysUser;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    private BlogMapper blogMapper;

    @Resource
    private BlogLikedMapper blogLikedMapper;

    @Resource
    private ArticleService articleService;

    @Resource
    private RemoteUserService remoteUserService;

    @Resource
    private RocketMQTemplate rocketMQTemplate;


    @Override
    public List<IndexBlogVo> getBlogList(BlogListDto dto) {
        BlogQueryModeEnum queryMode = BlogQueryModeEnum.getEnum(dto.getQueryMode());
        List<Blog> blogList = new ArrayList<>();
        Blog blog = new Blog();

        switch (queryMode) {
            case BLOG_MANEGE:
                blog.setAuthorId(SecurityUtils.getUserId());
                blog.setType(BlogTypeEnum.TWEET.getType());
                blogList = blogMapper.getArticleList(
                        blog,
                        dto.getPageSize(),
                        SqlUtil.getOffset(dto.getPageNum(), dto.getPageSize()),
                        queryMode.getOrderEnum().getOrder());

                break;
            case REGULAR:
                // TODO 需要重构
                blog.setType(BlogTypeEnum.TWEET.getType());
                blogList = blogMapper.getArticleList(
                        blog,
                        dto.getPageSize(),
                        0,
                        queryMode.getOrderEnum().getOrder());
                break;
            case LATEST:
                blog.setAuthorId(dto.getUserId());
                blog.setType(BlogTypeEnum.TWEET.getType());
                blogList = blogMapper.getArticleList(
                        blog,
                        1,
                        0,
                        queryMode.getOrderEnum().getOrder()
                );
                break;
            case PERSONAL_BLOG:
                blog.setAuthorId(dto.getUserId());
                blog.setType(BlogTypeEnum.TWEET.getType());
                blogList = blogMapper.getArticleList(
                        blog,
                        dto.getPageSize(),
                        SqlUtil.getOffset(dto.getPageNum(), dto.getPageSize()),
                        queryMode.getOrderEnum().getOrder()
                );
                break;
            default:
                // unreachable
                break;
        }

        // 组装返回值
        return packBlogVoList(blogList);

    }

    @Override
    public int deletePersonBlog(IdDto dto) {
        return articleService.deleteArticle(dto);
    }

    @Override
    public List<IndexBlogVo> getRandomBlog() {
        List<Blog> blogList = blogMapper.getTempBlogList();

        return packBlogVoList(blogList);

    }

    // 将数据表中查询出的 blogList 打包为前端需要的数据格式
    private List<IndexBlogVo> packBlogVoList(List<Blog> blogList) {
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
            blogVo.setAuthorId(sysUser.getUserId());
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

            // 获取picUrls
            String picUrls = blog.getPicUrls();
            if (picUrls != null && picUrls.length() > 0) {
                blogVo.setPicUrlList(Arrays.asList(picUrls.split("[,]")));
            }

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

        Blog blog = new Blog();
        blog.setId(blogId);
        List<Blog> blogList = blogMapper.getArticleList(blog, null, null, null);
        blog = blogList.get(0);

        if (flag > 0) {
            // 通知下游改变blog点赞计数
            BlogMessage message = new BlogMessage();
            message.setMessageId(blogId);
            message.setBlogId(blogId);
            message.setBlogType(blog.getType());
            message.setOperateType(typeEnum.getType());
            message.setType(BlogMessage.MessageType.LIKE.getType());
            rocketMQTemplate.asyncSendOrderly(
                    MqTopicConstants.BLOG_TOPIC,
                    message,
                    String.valueOf(message.getMessageId()),
                    new DefaultCallBack<>(this.getClass(), message)
            );

            // 通知下游改变用户点赞计数
            Long authorId = blog.getAuthorId();
            UserMessage userMessage = new UserMessage();
            userMessage.setUserId(authorId);
            userMessage.setMessageId(authorId);
            userMessage.setType(UserMessage.MessageType.LIKE.getType());
            userMessage.setOperateType(typeEnum.getType());
            rocketMQTemplate.asyncSendOrderly(
                    MqTopicConstants.USER_TOPIC,
                    userMessage,
                    String.valueOf(message.getMessageId()),
                    new DefaultCallBack<>(this.getClass(), userMessage)
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

    @Override
    public BlogDetailVo getBlogDetail(Long blogId) {
        BlogDetailVo vo = new BlogDetailVo();
        Blog blog = new Blog();
        blog.setId(blogId);
        List<Blog> list = blogMapper.getArticleList(blog, null, null, null);
        if (CollectionUtils.isEmpty(list)) {
            throw new ServiceException("想法不存在！");
        }
        blog = list.get(0);
        vo.setContent(blog.getPreview());
        vo.setCommentCnt(blog.getCommentCnt());
        vo.setViewCnt(blog.getViewCnt());
        vo.setLikeCnt(blog.getLikeCnt());
        if (StringUtils.isNotEmpty(blog.getPicUrls())) {
            vo.setPicUrlList(Arrays.asList(blog.getPicUrls().split("[,]")));
        }

        // 向下游发送通知,增加blog浏览量计数
        BlogMessage message = new BlogMessage();
        message.setMessageId(blogId);
        message.setBlogId(blogId);
        message.setBlogType(blog.getType());
        message.setOperateType(OperateType.ADD.getType());
        message.setType(BlogMessage.MessageType.VIEW.getType());
        rocketMQTemplate.asyncSendOrderly(
                MqTopicConstants.BLOG_TOPIC,
                message,
                String.valueOf(message.getMessageId()),
                new DefaultCallBack<>(this.getClass(), message)
        );

        return vo;
    }


}
