package com.ruoyi.common.mq.domain.blog;

import com.ruoyi.common.mq.domain.BaseMessage;
import com.ruoyi.common.mq.enums.OperateType;

public class CommentLikeMessage extends BaseMessage {

    private Long commentId;

    /**
     * {@link OperateType}
     */
    private Integer operateType;

    private Long userId;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CommentLikeMessage{" +
                "commentId=" + commentId +
                ", operateType=" + operateType +
                ", userId=" + userId +
                "} " + super.toString();
    }
}
