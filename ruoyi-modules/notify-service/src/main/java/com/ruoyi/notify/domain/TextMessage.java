package com.ruoyi.notify.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;

public class TextMessage extends BaseEntity {

    private Long id;

    private Long senderId;

    private Long receiverId;

    private String content;

    private Integer receiverHasRead;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getReceiverHasRead() {
        return receiverHasRead;
    }

    public void setReceiverHasRead(Integer receiverHasRead) {
        this.receiverHasRead = receiverHasRead;
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", content='" + content + '\'' +
                ", receiverHasRead=" + receiverHasRead +
                "} " + super.toString();
    }
}
