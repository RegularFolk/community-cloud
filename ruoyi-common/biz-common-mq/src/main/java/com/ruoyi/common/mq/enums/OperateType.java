package com.ruoyi.common.mq.enums;

import com.ruoyi.common.core.exception.ServiceException;

public enum OperateType {
    ADD(1, "添加"),
    CANCEL(2, "取消");

    private final Integer type;

    private final String typeDesc;

    OperateType(Integer type, String typeDesc) {
        this.type = type;
        this.typeDesc = typeDesc;
    }

    public static OperateType getEnum(Integer type) {
        for (OperateType value : values()) {
            if (value.type.equals(type)) {
                return value;
            }
        }
        throw new ServiceException("枚举值不存在！");
    }

    public Integer getType() {
        return type;
    }

    public String getTypeDesc() {
        return typeDesc;
    }
}
