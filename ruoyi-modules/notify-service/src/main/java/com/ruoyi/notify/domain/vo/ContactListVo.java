package com.ruoyi.notify.domain.vo;

public class ContactListVo {

    private Long contactId;

    private String avatar;

    private String contactName;

    private Long unReadCnt;

    private String latestMsgPreview;

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Long getUnReadCnt() {
        return unReadCnt;
    }

    public void setUnReadCnt(Long unReadCnt) {
        this.unReadCnt = unReadCnt;
    }

    public String getLatestMsgPreview() {
        return latestMsgPreview;
    }

    public void setLatestMsgPreview(String latestMsgPreview) {
        this.latestMsgPreview = latestMsgPreview;
    }

    @Override
    public String toString() {
        return "ContactListVo{" +
                "contactId=" + contactId +
                ", avatar='" + avatar + '\'' +
                ", contactName='" + contactName + '\'' +
                ", unReadCnt=" + unReadCnt +
                ", latestMsgPreview='" + latestMsgPreview + '\'' +
                '}';
    }
}
