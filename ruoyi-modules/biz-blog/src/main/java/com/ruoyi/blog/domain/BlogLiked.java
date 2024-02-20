package com.ruoyi.blog.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 随笔点赞表 blog_liked
 */
public class BlogLiked extends BaseEntity {

    private Long id;

    private Long userId;

    private Long blogId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    @Override
    public String toString() {
        return "BlogLiked{" +
                "id=" + id +
                ", userId=" + userId +
                ", blogId=" + blogId +
                "} " + super.toString();
    }
}
