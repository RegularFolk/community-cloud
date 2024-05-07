package com.ruoyi.blog.domain.dto;

public class ChangeAccessStatusDto {

    private String phoneNumber;

    private Integer status;

    @Override
    public String toString() {
        return "ChangeAccessStatusDto{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", status=" + status +
                '}';
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
