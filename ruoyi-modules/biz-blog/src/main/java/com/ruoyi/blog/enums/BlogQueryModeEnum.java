package com.ruoyi.blog.enums;

import com.ruoyi.common.core.exception.ServiceException;

public enum BlogQueryModeEnum {

    REGULAR(1, "正常模式", BlogOrderEnum.RAND),
    BLOG_MANEGE(2, "个人管理", BlogOrderEnum.TIME_DESC);


    private final int mode;

    private final String modeDesc;

    // 查询模式对应的排序要求
    private final BlogOrderEnum orderEnum;

    BlogQueryModeEnum(int mode, String modeDesc, BlogOrderEnum orderEnum) {
        this.mode = mode;
        this.modeDesc = modeDesc;
        this.orderEnum = orderEnum;
    }

    public int getMode() {
        return mode;
    }

    public String getModeDesc() {
        return modeDesc;
    }

    public BlogOrderEnum getOrderEnum() {
        return orderEnum;
    }

    public static BlogQueryModeEnum getEnum(int mode) {
        for (BlogQueryModeEnum value : values()) {
            if (value.getMode() == mode) {
                return value;
            }
        }
        throw new ServiceException("枚举值不存在！");
    }

}
