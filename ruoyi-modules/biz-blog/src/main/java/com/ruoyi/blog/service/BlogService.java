package com.ruoyi.blog.service;

import com.ruoyi.blog.domain.dto.PostCommentDto;
import com.ruoyi.blog.domain.vo.BlogCommentVo;
import com.ruoyi.blog.domain.vo.IndexBlogVo;

import java.util.List;

public interface BlogService {
    List<IndexBlogVo> getRandomBlog();

    BlogCommentVo getComments(Long blogId, Long start, Boolean refreshFlag);

    boolean postComment(PostCommentDto dto);
}
