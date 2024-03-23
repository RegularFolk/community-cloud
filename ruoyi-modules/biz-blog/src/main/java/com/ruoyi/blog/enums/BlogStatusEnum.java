package com.ruoyi.blog.enums;

public enum BlogStatusEnum {

    PUBLISHED(1, "已发布"),
    PRIVATE(2, "仅自己可见"),
    ACCEPTED(3, "回答被采纳");


    private final int status;
    private final String statusDesc;

    BlogStatusEnum(int status, String statusDesc) {
        this.status = status;
        this.statusDesc = statusDesc;
    }

    public int getStatus() {
        return status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }
}
