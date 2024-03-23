package com.ruoyi.blog.domain.vo;

import com.ruoyi.common.core.domain.UserBasicInfoVo;

/**
 * 答疑广场出参
 */
public class QtnSquareVo {
    private Long qtnId;

    private UserBasicInfoVo author;

    private String title;

    private String preview;

    private String publishTime;

    private Long ansCnt;

    private Long likeCnt;

    private Long viewCnt;

    private Long collectCnt;

    private Boolean liked;

    private Boolean collected;

    public Long getQtnId() {
        return qtnId;
    }

    public void setQtnId(Long qtnId) {
        this.qtnId = qtnId;
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

    public Long getAnsCnt() {
        return ansCnt;
    }

    public void setAnsCnt(Long ansCnt) {
        this.ansCnt = ansCnt;
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
        return "QtnSquareVo{" +
                "qtnId=" + qtnId +
                ", author=" + author +
                ", title='" + title + '\'' +
                ", preview='" + preview + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", ansCnt=" + ansCnt +
                ", likeCnt=" + likeCnt +
                ", viewCnt=" + viewCnt +
                ", collectCnt=" + collectCnt +
                ", liked=" + liked +
                ", collected=" + collected +
                '}';
    }
}
