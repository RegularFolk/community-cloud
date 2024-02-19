package com.ruoyi.common.mq.enums;

public enum OperateType {
    ADD(1, "添加"),
    CANCEL(2, "取消");

    private final Integer type;

    private final String typeDesc;

    OperateType(Integer type, String typeDesc) {
        this.type = type;
        this.typeDesc = typeDesc;
    }

    public Integer getType() {
        return type;
    }

    public String getTypeDesc() {
        return typeDesc;
    }
}
