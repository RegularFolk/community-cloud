package com.ruoyi.blog.domain.dto;

/**
 * 新建/编辑章节 接口入参
 */
public class PostChapterDto {

    private Long chapterId;

    private Long courseId;

    private String title;

    private String desc;

    private Integer chapterOrder;

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

    public Integer getChapterOrder() {
        return chapterOrder;
    }

    public void setChapterOrder(Integer chapterOrder) {
        this.chapterOrder = chapterOrder;
    }

    @Override
    public String toString() {
        return "PostChapterDto{" +
                "chapterId=" + chapterId +
                ", courseId=" + courseId +
                ", title=" + title +
                ", desc='" + desc + '\'' +
                ", chapterOrder=" + chapterOrder +
                '}';
    }
}
