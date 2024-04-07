package com.ruoyi.blog.domain.vo;

import com.ruoyi.common.core.domain.UserBasicInfoVo;

public class ArticleVo {

    private Long articleId;

    private Long authorId;

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

    private Long collectCnt;

    private Boolean collected;

    private Integer status;

    private Boolean isLiked;

    private Boolean accepted;

    private UserBasicInfoVo userBasicInfo;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
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

    public Long getCollectCnt() {
        return collectCnt;
    }

    public void setCollectCnt(Long collectCnt) {
        this.collectCnt = collectCnt;
    }

    public Boolean getCollected() {
        return collected;
    }

    public void setCollected(Boolean collected) {
        this.collected = collected;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getLiked() {
        return isLiked;
    }

    public void setLiked(Boolean liked) {
        isLiked = liked;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public UserBasicInfoVo getUserBasicInfo() {
        return userBasicInfo;
    }

    public void setUserBasicInfo(UserBasicInfoVo userBasicInfo) {
        this.userBasicInfo = userBasicInfo;
    }

    @Override
    public String toString() {
        return "ArticleVo{" +
                "articleId=" + articleId +
                ", authorId=" + authorId +
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
                ", collectCnt=" + collectCnt +
                ", collected=" + collected +
                ", status=" + status +
                ", isLiked=" + isLiked +
                ", accepted=" + accepted +
                ", userBasicInfo=" + userBasicInfo +
                '}';
    }
}
