package com.ruoyi.blog.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.web.domain.BaseEntity;

import java.util.Date;

/**
 *
 * 博客表 blog
 *
 */
public class Blog extends BaseEntity {

    private Long id;

    private Long authorId;

    private String preview;

    private Integer status;

    private Integer deleted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date releaseTime;

    private Long likeCnt;

    private Long viewCnt;

    private Long commentCnt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
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

    public Long getCommentCnt() {
        return commentCnt;
    }

    public void setCommentCnt(Long commentCnt) {
        this.commentCnt = commentCnt;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", authorId=" + authorId +
                ", preview='" + preview + '\'' +
                ", status=" + status +
                ", deleted=" + deleted +
                ", releaseTime=" + releaseTime +
                ", likeCnt=" + likeCnt +
                ", viewCnt=" + viewCnt +
                ", commentCnt=" + commentCnt +
                '}';
    }
}
