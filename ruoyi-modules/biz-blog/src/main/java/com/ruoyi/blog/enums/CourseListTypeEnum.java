package com.ruoyi.blog.enums;

public enum CourseListTypeEnum {


    AUTHOR(1, "指定用户查询"),
    RANDOM(2, "随机查询"),
    TITLE_SEARCH(3, "标题查询"),
    HOTTEST(4, "最热门"),
    LATEST(5, "最新发布");

    private final Integer type;

    private final String desc;

    public static CourseListTypeEnum getEnum(Integer type) {
        for (CourseListTypeEnum value : values()) {
            if (value.getType().equals(type)) {
                return value;
            }
        }
        throw new RuntimeException("枚举值不存在！");
    }

    CourseListTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
