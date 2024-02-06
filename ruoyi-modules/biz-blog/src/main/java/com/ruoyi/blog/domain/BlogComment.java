package com.ruoyi.blog.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.web.domain.BaseEntity;

import java.util.Date;

/**
 * 博客评论表 blog_comment
 */
public class BlogComment extends BaseEntity {

    private Long id;

    private Long blogId;

    private Long senderId;

    private Long receiverId;

    private Long parentId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Integer deleted;

    private String content;

    private Long likeCnt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getLikeCnt() {
        return likeCnt;
    }

    public void setLikeCnt(Long likeCnt) {
        this.likeCnt = likeCnt;
    }

    @Override
    public String toString() {
        return "BlogComment{" +
                "id=" + id +
                ", blogId=" + blogId +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", parentId=" + parentId +
                ", createTime=" + createTime +
                ", deleted=" + deleted +
                ", content='" + content + '\'' +
                ", likeCnt=" + likeCnt +
                '}';
    }
}
