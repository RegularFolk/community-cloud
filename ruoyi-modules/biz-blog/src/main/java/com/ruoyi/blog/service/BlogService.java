package com.ruoyi.blog.service;

import com.ruoyi.blog.domain.dto.BlogLikeDto;
import com.ruoyi.blog.domain.dto.BlogListDto;
import com.ruoyi.blog.domain.dto.PostBlogDto;
import com.ruoyi.blog.domain.vo.BlogDetailVo;
import com.ruoyi.blog.domain.vo.IndexBlogVo;
import com.ruoyi.common.core.domain.IdDto;

import java.util.List;

public interface BlogService {
    List<IndexBlogVo> getRandomBlog();


    int blogLike(BlogLikeDto dto);

    long postBlog(PostBlogDto dto);

    BlogDetailVo getBlogDetail(Long blogId);

    List<IndexBlogVo> getBlogList(BlogListDto dto);

    int deletePersonBlog(IdDto dto);
}
