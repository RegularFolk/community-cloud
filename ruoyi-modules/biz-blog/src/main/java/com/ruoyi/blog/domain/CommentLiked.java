package com.ruoyi.blog.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 评论点赞表 comment_liked
 */
public class CommentLiked extends BaseEntity {

    private Long id;

    private Long userId;

    private Long commentId;

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
        return commentId;
    }

    public void setBlogId(Long blogId) {
        this.commentId = blogId;
    }

    @Override
    public String toString() {
        return "CommentLiked{" +
                "id=" + id +
                ", userId=" + userId +
                ", commentId=" + commentId +
                "} " + super.toString();
    }
}
