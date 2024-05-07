package com.ruoyi.blog.domain.vo;

public class AccessGenerateVo {

    private String phoneNumber;

    private String accessKeyId;

    private String accessKeySecret;

    public AccessGenerateVo() {
    }

    public AccessGenerateVo(String phoneNumber, String accessKeyId, String accessKeySecret) {
        this.phoneNumber = phoneNumber;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
    }

    @Override
    public String toString() {
        return "AccessGenerateVo{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", accessKeyId='" + accessKeyId + '\'' +
                ", accessKeySecret='" + accessKeySecret + '\'' +
                '}';
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
}
