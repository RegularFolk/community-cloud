package com.ruoyi.common.mq.domain.blog;

import com.ruoyi.common.mq.domain.BaseMessage;

public class ViewMessage extends BaseMessage {

    private Long blogId;

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    @Override
    public String toString() {
        return "ViewMessage{" +
                "messageId=" + getMessageId() +
                ", blogId=" + blogId +
                '}';
    }
}
