package com.ruoyi.blog.controller;

import com.ruoyi.blog.domain.dto.*;
import com.ruoyi.blog.domain.vo.ArticleCollectQueryVo;
import com.ruoyi.blog.domain.vo.ArticleQueryVo;
import com.ruoyi.blog.domain.vo.ArticleVo;
import com.ruoyi.blog.domain.vo.PersonClassVo;
import com.ruoyi.blog.service.ArticleService;
import com.ruoyi.common.core.domain.IdDto;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController extends BaseController {

    @Resource
    private ArticleService articleService;

    /**
     * 上传文章（新增和修改）
     */
    @PostMapping("/postArticle")
    public R<Long> postArticle(@RequestBody PostArticleDto dto) {
        Long blogId = articleService.postArticle(dto);
        return R.ok(blogId);
    }

    /**
     * 添加个人分类
     */
    @PostMapping("/postPersonClass")
    public R<Long> postPersonClass(@RequestBody PostArticleClassDto dto) {
        int flag = articleService.postPersonClassification(dto);
        return flag > 0 ? R.ok() : R.fail("个人分类名称不可重复！");
    }

    /**
     * 删除个人分类
     */
    @PostMapping("/deletePersonClass")
    public R<Void> deletePersonClass(@RequestBody DeletePersonClassDto dto) {
        int cnt = articleService.deletePersonClass(dto);
        return cnt > 0 ? R.ok() : R.fail("删除失败，请稍后重试！");
    }

    /**
     * 删除个人随笔
     */
    @PostMapping("/deleteArticle")
    public R<Void> deleteArticle(@RequestBody IdDto dto) {
        int cnt = articleService.deleteArticle(dto);
        return cnt > 0 ? R.ok() : R.fail("删除失败，请稍后重试！");
    }

    /**
     * 随笔管理列表查询
     */
    @PostMapping("/list")
    public TableDataInfo getArticleList(@RequestBody ArticleQueryDto dto) {
        List<ArticleQueryVo> voList = articleService.getArticleList(dto);
        TableDataInfo dataTable = getDataTable(voList);
        dataTable.setTotal(articleService.getTotalArticle(dto));
        return dataTable;

    }

    /**
     * 获取个人分类列表
     */
    @GetMapping("/getPersonClassList")
    public R<List<PersonClassVo>> getPersonClassList() {
        Long userId = SecurityUtils.getUserId();
        List<PersonClassVo> voList = articleService.getPersonClassList(userId);
        return R.ok(voList);
    }

    /**
     * 查询个人随笔
     */
    @GetMapping("/getArticle/{articleId}")
    public R<ArticleVo> getArticle(@PathVariable("articleId") Long articleId) {
        ArticleVo vo = articleService.getArticle(articleId);
        return R.ok(vo);
    }

    /**
     * 收藏随笔
     */
    @PostMapping("/collect")
    public R<Void> collect(@RequestBody IdDto dto) {
        int flag = articleService.collect(dto);
        return flag > 0 ? R.ok() : R.fail("收藏失败！请稍后重试或者联系管理员！");
    }

    /**
     * 取消收藏
     */
    @PostMapping("/cancelCollect")
    public R<Void> cancelCollect(@RequestBody IdDto dto) {
        int flag = articleService.cancelCollect(dto);
        return flag > 0 ? R.ok() : R.fail("取消失败！请稍后重试或者联系管理员");
    }

    /**
     * 收藏列表查询
     */
    @PostMapping("/collect/list")
    public R<ArticleCollectQueryVo> collectList(@RequestBody ArticleCollectQueryDto dto) {
        ArticleCollectQueryVo vo = articleService.collectList(dto);
        return R.ok(vo);
    }


}
