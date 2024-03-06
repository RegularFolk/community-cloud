package com.ruoyi.notify.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;

public class ContactBook extends BaseEntity {

    private Long userId;

    private Long contactId;

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

    @Override
    public String toString() {
        return "ContactBook{" +
                "userId=" + userId +
                ", contactId=" + contactId +
                "} " + super.toString();
    }
}
