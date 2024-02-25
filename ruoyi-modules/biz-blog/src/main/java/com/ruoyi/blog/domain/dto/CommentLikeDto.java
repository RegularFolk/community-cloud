package com.ruoyi.blog.domain.dto;

import com.ruoyi.common.mq.enums.OperateType;

/**
 * 评论点赞/取消点赞 入参
 */
public class CommentLikeDto {

    private Long commentId;

    /**
     * {@link OperateType}
     */
    private Integer operateType;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    @Override
    public String toString() {
        return "CommentLikeDto{" +
                "commentId=" + commentId +
                ", operateType=" + operateType +
                '}';
    }
}
