package com.ruoyi.blog.controller;

import com.ruoyi.blog.domain.vo.BlogCommentVo;
import com.ruoyi.blog.domain.vo.IndexBlogVo;
import com.ruoyi.blog.service.BlogService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.controller.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/blogController")
public class BlogController extends BaseController {

    @Resource
    private BlogService blogService;

    /**
     * 获取博客列表
     */
    @GetMapping("/randomList")
    public R<List<IndexBlogVo>> getRandomBlog() {
        startPage();
        List<IndexBlogVo> blogList = blogService.getRandomBlog();
        return R.ok(blogList);
    }

    /**
     * 获取评论
     */
    @GetMapping("/getComment/{blogId}/{start}")
    public R<BlogCommentVo> getComment(
            @PathVariable("blogId") Long blogId,
            @PathVariable("start") Long start) {
        BlogCommentVo blogCommentVo = blogService.getComments(blogId, start);
        return R.ok(blogCommentVo);
    }

}
