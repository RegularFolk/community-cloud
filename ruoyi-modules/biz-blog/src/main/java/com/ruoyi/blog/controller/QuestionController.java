package com.ruoyi.blog.controller;

import com.ruoyi.blog.domain.dto.PostQtnDto;
import com.ruoyi.blog.domain.dto.QtnAnsAcptDto;
import com.ruoyi.blog.domain.dto.QtnAnsListDto;
import com.ruoyi.blog.domain.dto.QtnSquareDto;
import com.ruoyi.blog.domain.vo.ArticleVo;
import com.ruoyi.blog.domain.vo.QtnAnsVo;
import com.ruoyi.blog.domain.vo.QtnSquareVo;
import com.ruoyi.blog.service.QtnService;
import com.ruoyi.common.core.domain.IdDto;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.mq.enums.BlogTypeEnum;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/qtn")
public class QuestionController extends BaseController {

    @Resource
    private QtnService qtnService;

    /**
     * 新增/编辑/回答问题
     */
    @PostMapping("/postQtn")
    public AjaxResult postQtn(@RequestBody PostQtnDto dto) {
        Long flag = qtnService.postQtn(dto);
        return flag > 0 ? AjaxResult.success(flag) : AjaxResult.error("问题操作失败！请稍后重试或联系管理员");
    }

    /**
     * 删除问题
     */
    @PostMapping("/delQtn")
    public AjaxResult delQtn(@RequestBody IdDto dto) {
        int flag = qtnService.delQtn(dto);
        return flag > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 问题列表查询，采用评论一样的下拉展示更多的策略
     */
    @PostMapping("/list")
    public AjaxResult list(@RequestBody QtnSquareDto dto) {
        List<QtnSquareVo> voList = qtnService.list(dto);
        return AjaxResult.success(voList);
    }

    /**
     * 查询问题详情
     */
    @PostMapping("/detail")
    public AjaxResult detail(@RequestBody IdDto dto) {
        ArticleVo vo = qtnService.detail(dto);
        return AjaxResult.success(vo);
    }

    /**
     * 回答列表查询
     */
    @PostMapping("/ansList")
    public AjaxResult ansList(@RequestBody QtnAnsListDto dto) {
        QtnAnsVo vo = qtnService.ansList(dto);
        return AjaxResult.success(vo);
    }

    /**
     * 关注问题
     */
    @PostMapping("/collect")
    public AjaxResult collect(@RequestBody IdDto dto) {
        int flag = qtnService.collect(dto, BlogTypeEnum.QUESTION);
        return flag > 0 ? AjaxResult.success() : AjaxResult.error("操作失败！请稍后重试或联系管理员");
    }

    /**
     * 采纳回答
     */
    @PostMapping("/accept")
    public AjaxResult accept(@RequestBody QtnAnsAcptDto dto) {
        int flag = qtnService.accept(dto);
        return flag > 0 ? AjaxResult.success() : AjaxResult.error("同一问题下不可采纳多个回答！");
    }
}
