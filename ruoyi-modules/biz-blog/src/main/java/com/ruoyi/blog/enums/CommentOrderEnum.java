package com.ruoyi.blog.enums;

import com.ruoyi.common.core.exception.ServiceException;

public enum CommentOrderEnum {

    TIME_ASC("1", "按时间升序"),
    TIME_DESC("2", "按时间降序"),
    LIKE_DESC("3", "按点赞数降序");

    private String order;

    private String orderDesc;

    CommentOrderEnum(String order, String orderDesc) {
        this.order = order;
        this.orderDesc = orderDesc;
    }

    public String getOrder() {
        return order;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public static CommentOrderEnum getEnum(String order) {
        for (CommentOrderEnum value : values()) {
            if (value.getOrder().equals(order)) {
                return value;
            }
        }
        throw new ServiceException("枚举值{" + order + "}不存在！");
    }
}
