package com.ruoyi.blog.domain.dto;

public class AccessListDto {
    private String phoneNumber;

    private Integer status;

    private Integer pageSize;

    private Integer pageNum;

    @Override
    public String toString() {
        return "AccessListDto{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", status=" + status +
                ", pageSize=" + pageSize +
                ", pageNum=" + pageNum +
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

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
}
