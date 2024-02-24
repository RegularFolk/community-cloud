package com.ruoyi.blog.service;

import com.ruoyi.blog.domain.dto.BlogLikeDto;
import com.ruoyi.blog.domain.dto.PostBlogDto;
import com.ruoyi.blog.domain.vo.IndexBlogVo;

import java.util.List;

public interface BlogService {
    List<IndexBlogVo> getRandomBlog();


    int blogLike(BlogLikeDto dto);

    long postBlog(PostBlogDto dto);
}
