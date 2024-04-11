package com.ruoyi.blog.controller;

import com.ruoyi.blog.domain.dto.PostChapterDto;
import com.ruoyi.blog.domain.dto.PostCourseDto;
import com.ruoyi.blog.domain.dto.PostVideoDto;
import com.ruoyi.blog.service.CourseService;
import com.ruoyi.common.core.domain.IdDto;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Resource
    private CourseService courseService;

    /**
     * 新建/编辑课程
     */
    @PostMapping("/postCourse")
    public AjaxResult postCourse(@RequestBody PostCourseDto dto) {
        int flag = courseService.postCourse(dto);
        return flag > 0 ? AjaxResult.success() : AjaxResult.error(AjaxResult.DEFAULT_ERROR_MSG);
    }

    /**
     * 新建/编辑章节
     */
    @PostMapping("/postChapter")
    public AjaxResult postChapter(@RequestBody PostChapterDto dto) {
        int flag = courseService.postChapter(dto);
        return flag > 0 ? AjaxResult.success() : AjaxResult.error(AjaxResult.DEFAULT_ERROR_MSG);
    }

    /**
     * 新建/编辑视频
     */
    @PostMapping("/postVideo")
    public AjaxResult postVideo(@RequestBody PostVideoDto dto) {
        int flag = courseService.postVideo(dto);
        return flag > 0 ? AjaxResult.success() : AjaxResult.error(AjaxResult.DEFAULT_ERROR_MSG);
    }

    /**
     * 删除课程
     */
    @PostMapping("/delCourse")
    public AjaxResult delCourse(@RequestBody IdDto dto) {
        int flag = courseService.delCourse(dto);
        return flag > 0 ? AjaxResult.success() : AjaxResult.error(AjaxResult.DEFAULT_ERROR_MSG);
    }

    /**
     * 删除章节
     */
    @PostMapping("/delChapter")
    public AjaxResult delChapter(@RequestBody IdDto dto) {
        int flag = courseService.delChapter(dto);
        return flag > 0 ? AjaxResult.success() : AjaxResult.error(AjaxResult.DEFAULT_ERROR_MSG);
    }

    /**
     * 删除视频
     */
    @PostMapping("/delVideo")
    public AjaxResult delVideo(@RequestBody IdDto dto) {
        int flag = courseService.delVideo(dto);
        return flag > 0 ? AjaxResult.success() : AjaxResult.error(AjaxResult.DEFAULT_ERROR_MSG);
    }

    /**
     * 课程列表查询
     */
    @PostMapping("/list")
    public AjaxResult list() {

        return AjaxResult.success();
    }

    /**
     * 查看课程详情页
     */
    @PostMapping("/courseDetail")
    public AjaxResult courseDetail() {

        return AjaxResult.success();
    }

    /**
     * 查看视频
     */
    @PostMapping("/videoDetail")
    public AjaxResult videoDetail() {

        return AjaxResult.success();
    }


}
