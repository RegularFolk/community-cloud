package com.ruoyi.blog.domain.vo;

/**
 * 推荐页面博客对象
 */
public class IndexBlogVo {

    private Long blogId;

    private String senderName;

    private String releaseTime;

    private Boolean isAuthorFollowed;

    private String preview;

    private Long viewCnt;

    private Long likeCnt;

    private Long commentCnt;

    private String avatar;

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

    @Override
    public String toString() {
        return "IndexBlogVo{" +
                "blogId=" + blogId +
                ", senderName='" + senderName + '\'' +
                ", releaseTime='" + releaseTime + '\'' +
                ", isAuthorFollowed=" + isAuthorFollowed +
                ", preview='" + preview + '\'' +
                ", viewCnt=" + viewCnt +
                ", likeCnt=" + likeCnt +
                ", commentCnt=" + commentCnt +
                ", avatar='" + avatar + '\'' +
                '}';
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
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

}
