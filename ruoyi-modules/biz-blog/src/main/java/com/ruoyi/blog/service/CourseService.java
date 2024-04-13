package com.ruoyi.blog.service;

import com.ruoyi.blog.domain.dto.CourseListDto;
import com.ruoyi.blog.domain.dto.PostChapterDto;
import com.ruoyi.blog.domain.dto.PostCourseDto;
import com.ruoyi.blog.domain.dto.PostVideoDto;
import com.ruoyi.blog.domain.vo.CourseDetailVo;
import com.ruoyi.blog.domain.vo.CourseListVo;
import com.ruoyi.blog.domain.vo.VideoDetailVo;
import com.ruoyi.common.core.domain.IdDto;

import java.util.List;

public interface CourseService {
    int postCourse(PostCourseDto dto);

    int postChapter(PostChapterDto dto);

    int postVideo(PostVideoDto dto);

    int delVideo(IdDto dto);

    int delCourse(IdDto dto);

    int delChapter(IdDto dto);

    List<CourseListVo> list(CourseListDto dto);

    long listTotal(CourseListDto dto);

    CourseDetailVo courseDetail(IdDto dto);

    VideoDetailVo videoDetail(IdDto dto);
}
