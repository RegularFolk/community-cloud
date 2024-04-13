package com.ruoyi.blog.domain.vo;

public class VideoDetailVo {

    private Long id;

    private String title;

    private String preview;

    private Long likeCnt;

    private Long commentCnt;

    private Long viewCnt;

    private Long CollectCnt;

    private Long courseId;

    private String videoId;

    private String videoUrl;

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

    public Long getLikeCnt() {
        return likeCnt;
    }

    public void setLikeCnt(Long likeCnt) {
        this.likeCnt = likeCnt;
    }

    public Long getCommentCnt() {
        return commentCnt;
    }

    public void setCommentCnt(Long commentCnt) {
        this.commentCnt = commentCnt;
    }

    public Long getViewCnt() {
        return viewCnt;
    }

    public void setViewCnt(Long viewCnt) {
        this.viewCnt = viewCnt;
    }

    public Long getCollectCnt() {
        return CollectCnt;
    }

    public void setCollectCnt(Long collectCnt) {
        CollectCnt = collectCnt;
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

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
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
        return "VideoDetailVo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", preview='" + preview + '\'' +
                ", likeCnt=" + likeCnt +
                ", commentCnt=" + commentCnt +
                ", viewCnt=" + viewCnt +
                ", CollectCnt=" + CollectCnt +
                ", courseId=" + courseId +
                ", videoId=" + videoId +
                ", videoUrl=" + videoUrl +
                ", chapterId=" + chapterId +
                ", chapterOrder=" + chapterOrder +
                '}';
    }
}
