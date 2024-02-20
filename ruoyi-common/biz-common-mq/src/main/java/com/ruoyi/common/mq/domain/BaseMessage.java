package com.ruoyi.common.mq.domain;

public class BaseMessage {

    private Long messageId;

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    @Override
    public String toString() {
        return "BaseMessage{" +
                "messageId=" + messageId +
                '}';
    }
}
