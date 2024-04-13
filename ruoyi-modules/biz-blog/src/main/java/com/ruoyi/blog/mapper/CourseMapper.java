package com.ruoyi.blog.mapper;

import com.ruoyi.blog.domain.Blog;
import com.ruoyi.blog.domain.Course;
import com.ruoyi.blog.domain.CourseChapter;
import com.ruoyi.common.core.domain.CntDto;
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

    List<CntDto> getLikeCntList(@Param("courseIdList") List<Long> courseIdList);

    List<CntDto> getViewCntList(@Param("courseIdList") List<Long> courseIdList);

    List<CntDto> getCommentCntList(@Param("courseIdList") List<Long> courseIdList);

    List<CntDto> getCollectCntList(@Param("courseIdList") List<Long> courseIdList);

    /**
     * course 表通用分页查询方法
     */
    List<Course> list(
            @Param("course") Course course,
            @Param("pageSize") Integer pageSize,
            @Param("offset") Integer offset,
            @Param("order") Integer order);

    /**
     * course 表 计数通用方法
     * @param course
     * @return
     */
    long listTotal(Course course);
}
