package com.ruoyi.common.mq.domain;

public class BlogViewMessage extends BaseMessage{

    private Long blogId;

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    @Override
    public String toString() {
        return "BlogViewMessage{" +
                "messageId=" + getMessageId() +
                ", blogId=" + blogId +
                '}';
    }
}
