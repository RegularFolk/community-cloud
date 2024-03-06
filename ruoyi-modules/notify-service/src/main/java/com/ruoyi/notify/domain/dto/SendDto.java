package com.ruoyi.notify.domain.dto;

public class SendDto {

    private Long receiverId;

    private String content;

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

    @Override
    public String toString() {
        return "SendDto{" +
                "receiverId=" + receiverId +
                ", content='" + content + '\'' +
                '}';
    }
}
