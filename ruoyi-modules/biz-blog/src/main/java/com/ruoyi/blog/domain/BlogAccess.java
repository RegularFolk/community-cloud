package com.ruoyi.blog.domain;

import com.ruoyi.blog.enums.AccessStatusEnum;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 *
 * 访问凭证表blog_access
 *
 */
public class BlogAccess extends BaseEntity {

    private String phoneNumber;

    private String accessKeyId;

    private String accessKeySecret;

    /**
     * {@link AccessStatusEnum}
     */
    private Integer status;

    private String lastUseTime;

    @Override
    public String toString() {
        return "BlogAccess{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", accessKeyId='" + accessKeyId + '\'' +
                ", accessKeySecret='" + accessKeySecret + '\'' +
                ", status=" + status +
                ", lastUseTime='" + lastUseTime + '\'' +
                "} " + super.toString();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLastUseTime() {
        return lastUseTime;
    }

    public void setLastUseTime(String lastUseTime) {
        this.lastUseTime = lastUseTime;
    }
}
