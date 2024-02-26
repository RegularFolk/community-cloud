package com.ruoyi.blog.controller;

import com.ruoyi.blog.domain.dto.BlogLikeDto;
import com.ruoyi.blog.domain.dto.BlogListDto;
import com.ruoyi.blog.domain.dto.PostBlogDto;
import com.ruoyi.blog.domain.vo.BlogDetailVo;
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
//        startPage();
        List<IndexBlogVo> blogList = blogService.getRandomBlog();
        return R.ok(blogList);
    }

    /**
     * 博客主页面列表查询
     */
    @PostMapping("/list")
    public R<List<IndexBlogVo>> getBlogList(@RequestBody BlogListDto dto) {
        List<IndexBlogVo> blogList = blogService.getBlogList(dto);
        return R.ok(blogList);
    }

    /**
     * 删除个人想法
     */
    @PostMapping("/delete")
    public R<Void> deleteBlog(@RequestBody IdDto dto) {
        int flag = blogService.deletePersonBlog(dto);
        return flag > 0 ? R.ok() : R.fail("删除失败！请稍后重试或者联系管理员");
    }


    /**
     * 点赞/取消点赞 接口
     */
    @PostMapping("/like")
    public R<Void> like(@RequestBody BlogLikeDto dto) {
        int flag = blogService.blogLike(dto);
        return flag > 0 ? R.ok() : R.fail("请勿重复点赞！");
    }

    /**
     * 提交想法
     */
    @PostMapping("/postBlog")
    public R<Void> postBlog(@RequestBody PostBlogDto dto) {
        long flag = blogService.postBlog(dto);
        return flag > 0 ? R.ok() : R.fail("提交失败！请稍后重试或者联系管理员");
    }

    /**
     * 获取想法完整内容
     */
    @GetMapping("/getBlog/{blogId}")
    public R<BlogDetailVo> getBlogDetail(@PathVariable("blogId") Long blogId) {
        BlogDetailVo vo = blogService.getBlogDetail(blogId);
        return R.ok(vo);
    }

}
