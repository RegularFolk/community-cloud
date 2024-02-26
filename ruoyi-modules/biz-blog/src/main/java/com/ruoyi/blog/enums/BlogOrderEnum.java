package com.ruoyi.blog.enums;

public enum BlogOrderEnum {
    NONE(0, "无需排序"),
    TIME_ASC(1, "按时间升序"),
    TIME_DESC(2, "按时间降序"),
    LIKE_DESC(3, "按点赞数降序"),
    RAND(4, "随机查询");


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
}
