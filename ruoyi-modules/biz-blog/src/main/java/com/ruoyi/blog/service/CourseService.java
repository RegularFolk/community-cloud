package com.ruoyi.blog.service;

import com.ruoyi.blog.domain.dto.PostChapterDto;
import com.ruoyi.blog.domain.dto.PostCourseDto;
import com.ruoyi.blog.domain.dto.PostVideoDto;
import com.ruoyi.common.core.domain.IdDto;

public interface CourseService {
    int postCourse(PostCourseDto dto);

    int postChapter(PostChapterDto dto);

    int postVideo(PostVideoDto dto);

    int delVideo(IdDto dto);

    int delCourse(IdDto dto);

    int delChapter(IdDto dto);
}
