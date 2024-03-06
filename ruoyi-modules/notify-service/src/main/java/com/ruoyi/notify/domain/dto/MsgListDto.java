package com.ruoyi.notify.domain.dto;

public class MsgListDto {

    private Long contactId;

    private Integer pageSize;

    private Long startMsgId;

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getStartMsgId() {
        return startMsgId;
    }

    public void setStartMsgId(Long startMsgId) {
        this.startMsgId = startMsgId;
    }

    @Override
    public String toString() {
        return "MsgListDto{" +
                "contactId=" + contactId +
                ", pageSize=" + pageSize +
                ", startMsgId=" + startMsgId +
                '}';
    }
}
