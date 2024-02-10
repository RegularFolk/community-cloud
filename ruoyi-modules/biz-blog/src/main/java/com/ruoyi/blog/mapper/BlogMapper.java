package com.ruoyi.blog.mapper;


import com.ruoyi.blog.domain.Blog;
import com.ruoyi.blog.domain.BlogContent;

import java.util.List;

public interface BlogMapper {


    /**
     * 临时测试方法，获取博客列表
     * @return
     */
    List<Blog> getTempBlogList();


    void insertBlog(Blog blog);

    void insertBlogContent(BlogContent blogContent);
}
