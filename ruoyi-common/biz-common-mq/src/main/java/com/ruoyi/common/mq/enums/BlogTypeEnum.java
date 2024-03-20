package com.ruoyi.common.mq.enums;

import com.ruoyi.common.core.exception.ServiceException;

public enum BlogTypeEnum {

    TWEET(1, "推文类型"),

    ARTICLE(2, "文章类型");

    private final int type;

    private final String typeDesc;

    public static BlogTypeEnum getEnum(Integer type) {
        for (BlogTypeEnum value : values()) {
            if (value.getType().equals(type)) {
                return value;
            }
        }
        throw new ServiceException("枚举不存在！");
    }

    BlogTypeEnum(int type, String typeDesc) {
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
