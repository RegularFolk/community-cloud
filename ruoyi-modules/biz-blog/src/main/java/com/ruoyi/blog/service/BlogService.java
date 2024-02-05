package com.ruoyi.blog.service;

import com.ruoyi.blog.domain.vo.IndexBlogVo;

import java.util.List;

public interface BlogService {
    List<IndexBlogVo> getRandomBlog();

}
