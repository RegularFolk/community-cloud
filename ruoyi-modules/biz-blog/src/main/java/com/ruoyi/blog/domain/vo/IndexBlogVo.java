package com.ruoyi.blog.domain.vo;

import java.util.List;

/**
 * 推荐页面博客对象
 */
public class IndexBlogVo {

    private Long blogId;

    private Long authorId;

    private String senderName;

    private String releaseTime;

    private Boolean isAuthorFollowed;

    private String preview;

    private Long viewCnt;

    private Long likeCnt;

    private Boolean liked;

    private Long commentCnt;

    private String avatar;

    private List<String> picUrlList;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getCommentCnt() {
        return commentCnt;
    }

    public void setCommentCnt(Long commentCnt) {
        this.commentCnt = commentCnt;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Boolean getAuthorFollowed() {
        return isAuthorFollowed;
    }

    public void setAuthorFollowed(Boolean authorFollowed) {
        isAuthorFollowed = authorFollowed;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
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

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public List<String> getPicUrlList() {
        return picUrlList;
    }

    public void setPicUrlList(List<String> picUrlList) {
        this.picUrlList = picUrlList;
    }

    @Override
    public String toString() {
        return "IndexBlogVo{" +
                "blogId=" + blogId +
                ", authorId=" + authorId +
                ", senderName='" + senderName + '\'' +
                ", releaseTime='" + releaseTime + '\'' +
                ", isAuthorFollowed=" + isAuthorFollowed +
                ", preview='" + preview + '\'' +
                ", viewCnt=" + viewCnt +
                ", likeCnt=" + likeCnt +
                ", liked=" + liked +
                ", commentCnt=" + commentCnt +
                ", avatar='" + avatar + '\'' +
                ", picUrlList=" + picUrlList +
                '}';
    }
}
