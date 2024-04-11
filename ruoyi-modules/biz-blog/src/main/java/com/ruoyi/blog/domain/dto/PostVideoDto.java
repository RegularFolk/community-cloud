package com.ruoyi.blog.domain.dto;

/**
 * 新建/编辑视频 接口入参
 */
public class PostVideoDto {

    private Long id;
    private String title;
    private String preview;
    private Long courseId;
    private String videoId;
    private Long chapterId;
    private Integer chapterOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public Integer getChapterOrder() {
        return chapterOrder;
    }

    public void setChapterOrder(Integer chapterOrder) {
        this.chapterOrder = chapterOrder;
    }

    @Override
    public String toString() {
        return "PostVideoDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", preview='" + preview + '\'' +
                ", courseId=" + courseId +
                ", videoId='" + videoId + '\'' +
                ", chapterId=" + chapterId +
                ", chapterOrder=" + chapterOrder +
                '}';
    }
}
