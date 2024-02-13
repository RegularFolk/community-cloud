package com.ruoyi.blog.enums;

public enum DeletePersonClassTypeEnum {

    MOVE_TO_DEFAULT(1, "内部随笔移动到默认分类"),
    DELETE_ALL(2, "内部随笔一并删除");

    private final int type;

    private final String typeDesc;

    DeletePersonClassTypeEnum(int type, String typeDesc) {
        this.type = type;
        this.typeDesc = typeDesc;
    }

    public int getType() {
        return type;
    }

    public String getTypeDesc() {
        return typeDesc;
    }
}
