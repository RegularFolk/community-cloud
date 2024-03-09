package com.ruoyi.notify.domain.mapper;

public class UnReadCnt {

    private Long senderId;

    private Long unreadCnt;

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getUnreadCnt() {
        return unreadCnt;
    }

    public void setUnreadCnt(Long unreadCnt) {
        this.unreadCnt = unreadCnt;
    }

    @Override
    public String toString() {
        return "UnReadCnt{" +
                "receiverId=" + senderId +
                ", unreadCnt=" + unreadCnt +
                '}';
    }
}
