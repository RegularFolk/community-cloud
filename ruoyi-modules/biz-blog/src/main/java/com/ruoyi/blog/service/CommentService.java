package com.ruoyi.blog.service;

import com.ruoyi.blog.domain.dto.CommentLikeDto;
import com.ruoyi.blog.domain.dto.GetCommentDto;
import com.ruoyi.blog.domain.dto.PostCommentDto;
import com.ruoyi.blog.domain.vo.BlogCommentVo;

public interface CommentService {

    BlogCommentVo getComments(GetCommentDto dto);

    boolean postComment(PostCommentDto dto);

    int commentLike(CommentLikeDto dto);
}
