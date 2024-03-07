package com.ruoyi.notify.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;

public class ContactBook extends BaseEntity {

    private Long userId;

    private Long contactId;

    private String msgPreview;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public String getMsgPreview() {
        return msgPreview;
    }

    public void setMsgPreview(String msgPreview) {
        this.msgPreview = msgPreview;
    }

    @Override
    public String toString() {
        return "ContactBook{" +
                "userId=" + userId +
                ", contactId=" + contactId +
                ", msgPreview='" + msgPreview + '\'' +
                "} " + super.toString();
    }
}
