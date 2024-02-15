package com.ruoyi.blog.domain.vo;

public class ArticleVo {

    private Long articleId;

    private String title;

    private String senderName;

    private String publishTime;

    private String contentFormatting;

    private String contentHtml;

    private Long personClassify;

    private Long articleClassify;

    private Long viewCnt;

    private Long likeCnt;

    private Long commentCnt;

    private Integer status;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentFormatting() {
        return contentFormatting;
    }

    public void setContentFormatting(String contentFormatting) {
        this.contentFormatting = contentFormatting;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public Long getPersonClassify() {
        return personClassify;
    }

    public void setPersonClassify(Long personClassify) {
        this.personClassify = personClassify;
    }

    public Long getArticleClassify() {
        return articleClassify;
    }

    public void setArticleClassify(Long articleClassify) {
        this.articleClassify = articleClassify;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public Long getViewCnt() {
        return viewCnt;
    }

    public void setViewCnt(Long viewCnt) {
        this.viewCnt = viewCnt;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ArticleVo{" +
                "articleId=" + articleId +
                ", title='" + title + '\'' +
                ", senderName='" + senderName + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", contentFormatting='" + contentFormatting + '\'' +
                ", contentHtml='" + contentHtml + '\'' +
                ", personClassify=" + personClassify +
                ", articleClassify=" + articleClassify +
                ", viewCnt=" + viewCnt +
                ", likeCnt=" + likeCnt +
                ", commentCnt=" + commentCnt +
                ", status=" + status +
                '}';
    }
}
