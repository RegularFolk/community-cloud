package com.ruoyi.blog.controller;

import com.ruoyi.blog.domain.dto.GetCommentDto;
import com.ruoyi.blog.domain.dto.PostCommentDto;
import com.ruoyi.blog.domain.vo.BlogCommentVo;
import com.ruoyi.blog.service.CommentService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.controller.BaseController;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/comment")
public class CommentController extends BaseController {

    @Resource
    CommentService commentService;


    /**
     * 获取评论
     */
    @PostMapping("/getComment")
    public R<BlogCommentVo> getComment(@RequestBody GetCommentDto dto) {
        BlogCommentVo blogCommentVo = commentService.getComments(dto);
        return R.ok(blogCommentVo);
    }

    /**
     * 添加评论
     */
    @PostMapping("/postComment")
    public R<Boolean> postComment(@RequestBody PostCommentDto dto) {
        boolean flag = commentService.postComment(dto);
        return flag ? R.ok() : R.fail("评论失败,请稍后再试!");
    }




}
