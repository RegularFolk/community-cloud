package com.ruoyi.blog.service.impl;

import com.ruoyi.blog.config.BlogConstants;
import com.ruoyi.blog.domain.Blog;
import com.ruoyi.blog.domain.BlogContent;
import com.ruoyi.blog.domain.dto.PostArticleDto;
import com.ruoyi.blog.domain.vo.IndexBlogVo;
import com.ruoyi.blog.enums.BlogStatusEnum;
import com.ruoyi.blog.enums.BlogTypeEnum;
import com.ruoyi.blog.mapper.BlogMapper;
import com.ruoyi.blog.service.BlogService;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.RemoteUserService;
import com.ruoyi.system.api.domain.SysUser;
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
    BlogMapper blogMapper;

    @Resource
    RemoteUserService remoteUserService;

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

        blogVoList = blogList.stream().map(blog -> {
            IndexBlogVo blogVo = new IndexBlogVo();
            SysUser sysUser = groupById.get(blog.getAuthorId()).get(0);

            blogVo.setBlogId(blog.getId());
            blogVo.setAuthorFollowed(false); // 测试方法默认设置为未关注
            blogVo.setReleaseTime(blog.getReleaseTime());
            blogVo.setPreview(blog.getPreview());
            blogVo.setLikeCnt(blog.getLikeCnt());
            blogVo.setViewCnt(blog.getViewCnt());
            blogVo.setCommentCnt(blog.getCommentCnt());
            blogVo.setSenderName(sysUser.getNickName());
            blogVo.setAvatar(sysUser.getAvatar());
            return blogVo;
        }).collect(Collectors.toList());


        return blogVoList;
    }

    @Override
    public Long postArticle(PostArticleDto dto) {
        Blog blog = new Blog();
        blog.setAuthorId(dto.getAuthorId());
        blog.setTitle(dto.getTitle());
        blog.setStatus(dto.getStatus());
        blog.setArticleClassify(dto.getArticleClassify());
        blog.setPersonClassify(dto.getPersonClassify());
        blog.setType(BlogTypeEnum.ARTICLE.getType());

        if (blog.getStatus().equals(BlogStatusEnum.PUBLISHED.getStatus())) {
            blog.setReleaseTime(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, new Date()));
        }

        // 获取内容的前三百字为预览
        String content = dto.getContent();
        if (content.length() <= BlogConstants.PREVIEW_LENGTH) {
            blog.setPreview(content);
        } else {
            blog.setPreview(content.substring(0, 300));
        }

        blogMapper.insertBlog(blog);

        BlogContent blogContent = new BlogContent();
        blogContent.setBlogId(blog.getId());
        blogContent.setContent(dto.getContent());
        blogContent.setContentFormatting(dto.getContentFormatting());
        blogMapper.insertBlogContent(blogContent);

        return blog.getId();
    }


}
