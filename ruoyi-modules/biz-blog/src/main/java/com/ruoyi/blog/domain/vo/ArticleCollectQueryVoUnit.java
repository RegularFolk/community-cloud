package com.ruoyi.blog.domain.vo;

public class ArticleCollectQueryVoUnit {
    private Long id;

    private String title;

    private String preview;

    private String authorName;

    private Long authorId;

    private Integer articleClassification;

    /**
     * com.ruoyi.blog.enums.BlogStatusEnum
     */
    private Integer status;

    private String collectTime;

    private String publishTime;

    private Long likeCnt;

    private Long commentCnt;

    private Long viewCnt;

    private Long collectCnt;

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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Integer getArticleClassification() {
        return articleClassification;
    }

    public void setArticleClassification(Integer articleClassification) {
        this.articleClassification = articleClassification;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
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
        return collectCnt;
    }

    public void setCollectCnt(Long collectCnt) {
        this.collectCnt = collectCnt;
    }

    @Override
    public String toString() {
        return "ArticleCollectQueryVoUnit{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", preview='" + preview + '\'' +
                ", authorName='" + authorName + '\'' +
                ", authorId=" + authorId +
                ", articleClassification=" + articleClassification +
                ", status=" + status +
                ", collectTime='" + collectTime + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", likeCnt=" + likeCnt +
                ", commentCnt=" + commentCnt +
                ", viewCnt=" + viewCnt +
                ", collectCnt=" + collectCnt +
                '}';
    }

}
