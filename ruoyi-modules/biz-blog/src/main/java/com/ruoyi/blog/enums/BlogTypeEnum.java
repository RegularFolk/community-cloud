package com.ruoyi.blog.enums;

public enum BlogTypeEnum {

    TWEET(1, "推文类型"),

    ARTICLE(2, "文章类型");

    private final int type;

    private final String typeDesc;

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
