package com.ruoyi.blog.enums;

import com.ruoyi.common.core.exception.ServiceException;

public enum ArticleRankEnum {
    VIEW(1, "浏览量"),
    COLLECT(2, "收藏量"),
    LIKE(3, "点赞量");

    ArticleRankEnum(Integer type, String typeDesc) {
        this.type = type;
        this.typeDesc = typeDesc;
    }

    private final Integer type;

    private final String typeDesc;

    public Integer getType() {
        return type;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public static ArticleRankEnum getEnum(int type) {
        for (ArticleRankEnum value : values()) {
            if (value.getType().equals(type)) {
                return value;
            }
        }
        throw new ServiceException("枚举不存在！");
    }
}
