package com.ruoyi.blog.domain.vo;

import com.ruoyi.blog.enums.ArticleClassificationEnum;
import com.ruoyi.common.core.domain.UserBasicInfoVo;

/**
 * 随笔广场出参
 */
public class ArticleSquareVo {

    private Long articleId;

    /**
     * {@link ArticleClassificationEnum}
     */
    private Integer articleClassification;

    private UserBasicInfoVo author;

    private String title;

    private String preview;

    private String publishTime;

    private Long commentCnt;

    private Long likeCnt;

    private Long viewCnt;

    private Long collectCnt;

    private Boolean liked;

    private Boolean collected;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Integer getArticleClassification() {
        return articleClassification;
    }

    public void setArticleClassification(Integer articleClassification) {
        this.articleClassification = articleClassification;
    }

    public UserBasicInfoVo getAuthor() {
        return author;
    }

    public void setAuthor(UserBasicInfoVo author) {
        this.author = author;
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

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public Long getCommentCnt() {
        return commentCnt;
    }

    public void setCommentCnt(Long commentCnt) {
        this.commentCnt = commentCnt;
    }

    public Long getLikeCnt() {
        return likeCnt;
    }

    public void setLikeCnt(Long likeCnt) {
        this.likeCnt = likeCnt;
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

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public Boolean getCollected() {
        return collected;
    }

    public void setCollected(Boolean collected) {
        this.collected = collected;
    }

    @Override
    public String toString() {
        return "ArticleSquareVo{" +
                "articleId=" + articleId +
                ", articleClassification=" + articleClassification +
                ", author=" + author +
                ", title='" + title + '\'' +
                ", preview='" + preview + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", commentCnt=" + commentCnt +
                ", likeCnt=" + likeCnt +
                ", viewCnt=" + viewCnt +
                ", collectCnt=" + collectCnt +
                ", liked=" + liked +
                ", collected=" + collected +
                '}';
    }
}
