package com.ruoyi.blog.enums;

import com.ruoyi.common.core.exception.ServiceException;

public enum AccessStatusEnum {
    ENABLE(1, "启用"),
    DISABLE(2, "禁用");


    private final int status;

    private final String desc;

    AccessStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static AccessStatusEnum getEnum(int status) {
        for (AccessStatusEnum value : values()) {
            if (value.getStatus() == status) {
                return value;
            }
        }
        throw new ServiceException("枚举不存在！");
    }

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
