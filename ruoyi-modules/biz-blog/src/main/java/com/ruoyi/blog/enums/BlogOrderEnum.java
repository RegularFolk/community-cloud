package com.ruoyi.blog.enums;

import com.ruoyi.common.core.exception.ServiceException;

public enum BlogOrderEnum {
    NONE(0, "无需排序"),
    TIME_ASC(1, "按创建时间升序"),
    TIME_DESC(2, "按创建时间降序"),
    LIKE_DESC(3, "按点赞数降序"),
    RAND(4, "随机查询"),
    PUBLISH_TIME_DESC(5, "按发布时间降序"),
    HOT_RANK(6, "综合热度降序（点赞量、收藏量、浏览量、创建时间）");


    private final int order;

    private final String orderDesc;

    BlogOrderEnum(int order, String orderDesc) {
        this.order = order;
        this.orderDesc = orderDesc;
    }

    public int getOrder() {
        return order;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public static BlogOrderEnum getEnum(int order) {
        for (BlogOrderEnum value : values()) {
            if (value.getOrder() == order) {
                return value;
            }
        }
        throw new ServiceException("枚举不存在！");
    }
}
