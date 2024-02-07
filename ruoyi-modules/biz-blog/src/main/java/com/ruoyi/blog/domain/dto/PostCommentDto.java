package com.ruoyi.blog.domain.dto;

/**
 * 添加评论入参
 */
public class PostCommentDto {

    private Long blogId;

    private Long senderId;

    private Long receiverId;

    private Long parentId;

    private String content;

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "PostCommentDto{" +
                "blogId=" + blogId +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", parentId=" + parentId +
                ", content=" + content +
                '}';
    }
}
