package com.ruoyi.blog.controller;

import com.ruoyi.blog.domain.dto.ArticleQueryDto;
import com.ruoyi.blog.domain.dto.PostArticleClassDto;
import com.ruoyi.blog.domain.dto.PostArticleDto;
import com.ruoyi.blog.domain.vo.ArticleQueryVo;
import com.ruoyi.blog.domain.vo.IndexBlogVo;
import com.ruoyi.blog.service.BlogService;
import com.ruoyi.common.core.domain.IdDto;
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

    /**
     * 添加个人分类
     */
    @PostMapping("/postPersonClass")
    public R<Long> postPersonClass(@RequestBody PostArticleClassDto dto) {
        Long classId = blogService.postPersonClassification(dto);
        return R.ok(classId);
    }

    /**
     * 删除个人分类
     */
    @PostMapping("/deletePersonClass")
    public R<Void> deletePersonClass(@RequestBody IdDto dto) {
        int cnt = blogService.deletePersonClass(dto.getId());
        return cnt > 0 ? R.ok() : R.fail("删除失败，请稍后重试！");
    }

    /**
     * 删除个人随笔
     */
    @PostMapping("/deleteArticle")
    public R<Void> deleteArticle(@RequestBody IdDto dto) {
        int cnt = blogService.deleteArticle(dto.getId());
        return cnt > 0 ? R.ok() : R.fail("删除失败，请稍后重试！");
    }

    /**
     * 随笔管理列表查询
     */
    @PostMapping("/article/list")
    public R<List<ArticleQueryVo>> getArticleList(@RequestBody ArticleQueryDto dto) {
        List<ArticleQueryVo> voList = blogService.getArticleList(dto);

        return R.ok(voList);

    }


}
