package com.ruoyi.blog.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 *
 * 课程表 course
 *
 */
public class Course extends BaseEntity {

    private Long courseId;

    private Long authorId;

    private String title;

    private String cover_pic;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover_pic() {
        return cover_pic;
    }

    public void setCover_pic(String cover_pic) {
        this.cover_pic = cover_pic;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", authorId=" + authorId +
                ", title='" + title + '\'' +
                ", cover_pic='" + cover_pic + '\'' +
                '}';
    }
}
