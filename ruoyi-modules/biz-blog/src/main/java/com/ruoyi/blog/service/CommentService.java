package com.ruoyi.blog.service;

import com.ruoyi.blog.domain.dto.PostCommentDto;
import com.ruoyi.blog.domain.vo.BlogCommentVo;

public interface CommentService {

    BlogCommentVo getComments(Long blogId, Long start, Boolean refreshFlag);

    boolean postComment(PostCommentDto dto);

}
