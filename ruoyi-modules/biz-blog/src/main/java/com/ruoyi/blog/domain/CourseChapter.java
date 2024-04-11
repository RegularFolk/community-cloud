package com.ruoyi.blog.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 *
 * 课程章节表 course_chapter
 *
 */
public class CourseChapter extends BaseEntity {

    private Long chapterId;

    private Long courseId;

    private Integer chapterOrder;

    private String title;

    private String desc;

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Integer getChapterOrder() {
        return chapterOrder;
    }

    public void setChapterOrder(Integer chapterOrder) {
        this.chapterOrder = chapterOrder;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "CourseChapter{" +
                "chapterId=" + chapterId +
                ", courseId=" + courseId +
                ", chapterOrder=" + chapterOrder +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
