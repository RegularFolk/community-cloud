package com.ruoyi.blog.controller;

import com.ruoyi.blog.domain.dto.PostArticleDto;
import com.ruoyi.blog.domain.vo.IndexBlogVo;
import com.ruoyi.blog.service.BlogService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.controller.BaseController;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/blogController")
public class BlogController extends BaseController {

    @Resource
    private BlogService blogService;

    /**
     * 获取博客列表(测试方法)
     */
    @GetMapping("/randomList")
    public R<List<IndexBlogVo>> getRandomBlog() {
        startPage();
        List<IndexBlogVo> blogList = blogService.getRandomBlog();
        return R.ok(blogList);
    }

    /**
     * 上传文章
     */
    @PostMapping("/postArticle")
    public R<Long> postArticle(@RequestBody PostArticleDto dto) {
        Long blogId = blogService.postArticle(dto);
        return R.ok(blogId);
    }

}
