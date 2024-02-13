package com.ruoyi.blog.service.impl;

import com.ruoyi.blog.config.BlogConstants;
import com.ruoyi.blog.domain.Blog;
import com.ruoyi.blog.domain.BlogContent;
import com.ruoyi.blog.domain.PersonalClassification;
import com.ruoyi.blog.domain.dto.ArticleQueryDto;
import com.ruoyi.blog.domain.dto.DeletePersonClassDto;
import com.ruoyi.blog.domain.dto.PostArticleClassDto;
import com.ruoyi.blog.domain.dto.PostArticleDto;
import com.ruoyi.blog.domain.vo.ArticleQueryVo;
import com.ruoyi.blog.domain.vo.IndexBlogVo;
import com.ruoyi.blog.domain.vo.PersonClassVo;
import com.ruoyi.blog.enums.BlogStatusEnum;
import com.ruoyi.blog.enums.BlogTypeEnum;
import com.ruoyi.blog.enums.DeletePersonClassTypeEnum;
import com.ruoyi.blog.mapper.BlogMapper;
import com.ruoyi.blog.service.BlogService;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.IdDto;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
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




}
