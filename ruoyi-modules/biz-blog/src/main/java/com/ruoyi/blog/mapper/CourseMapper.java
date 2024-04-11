package com.ruoyi.blog.mapper;

import com.ruoyi.blog.domain.Blog;
import com.ruoyi.blog.domain.Course;
import com.ruoyi.blog.domain.CourseChapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseMapper {
    int insertCourse(Course course);

    int updateCourse(Course course);

    int updateChapter(CourseChapter chapter);

    int insertChapter(CourseChapter chapter);

    int insertVideo(Blog blog);

    int updateVideo(Blog blog);

    int delChapterByIds(@Param("chapterIds") List<Long> chapterIds, @Param("userId") Long userId);

    List<CourseChapter> selectChapterByIds(@Param("chapterIds") List<Long> chapterIds);

    Course selectCourseById(@Param("id") Long id, @Param("userId") Long userId);

    List<CourseChapter> selectChapterByCourseId(Long courseId);

    int delCourse(@Param("courseId") Long courseId, @Param("userId") Long userId);
}
