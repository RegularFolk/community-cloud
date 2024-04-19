package com.ruoyi.blog.controller;

import com.ruoyi.blog.domain.dto.CourseListDto;
import com.ruoyi.blog.domain.dto.PostChapterDto;
import com.ruoyi.blog.domain.dto.PostCourseDto;
import com.ruoyi.blog.domain.dto.PostVideoDto;
import com.ruoyi.blog.domain.vo.CourseDetailVo;
import com.ruoyi.blog.domain.vo.CourseListVo;
import com.ruoyi.blog.domain.vo.VideoDetailVo;
import com.ruoyi.blog.service.CourseService;
import com.ruoyi.common.core.domain.IdDto;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.mq.enums.BlogTypeEnum;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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
        long flag = courseService.postCourse(dto);
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
    public AjaxResult list(@RequestBody CourseListDto dto) {
        List<CourseListVo> list = courseService.list(dto);
        long total = courseService.listTotal(dto);
        AjaxResult res = AjaxResult.success(list);
        res.put("total", total);
        return res;
    }

    /**
     * 查看课程详情页
     */
    @PostMapping("/courseDetail")
    public AjaxResult courseDetail(@RequestBody IdDto dto) {
        CourseDetailVo vo = courseService.courseDetail(dto);
        return AjaxResult.success(vo);
    }

    /**
     * 查看视频
     */
    @PostMapping("/videoDetail")
    public AjaxResult videoDetail(@RequestBody IdDto dto) {
        VideoDetailVo vo = courseService.videoDetail(dto);
        return AjaxResult.success(vo);
    }

    /**
     * 收藏视频
     */
    @PostMapping("/collect")
    public AjaxResult collect(@RequestBody IdDto dto) {
        int flag = courseService.collect(dto, BlogTypeEnum.VIDEO);
        return flag > 0 ? AjaxResult.success() : AjaxResult.error("操作失败！请稍后重试或联系管理员");
    }


}
