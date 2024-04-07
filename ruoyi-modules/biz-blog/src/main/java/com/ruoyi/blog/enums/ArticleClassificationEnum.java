package com.ruoyi.blog.enums;

public enum ArticleClassificationEnum {

    JI_SHU_FEN_XIANG(1, "技术分享"),
    XIN_WEN_ZI_XUN(2, "新闻资讯"),
    QV_WEN_TU_CAO(3, "趣闻吐槽"),
    XIN_DE_ZONG_JIE(4, "心得总结"),
    SUI_SUI_NIAN(5, "碎碎念"),
    QUESTION(6, "问题");

    private final int classification;

    private final String classDesc;

    ArticleClassificationEnum(int classification, String classDesc) {
        this.classification = classification;
        this.classDesc = classDesc;
    }

    public int getClassification() {
        return classification;
    }

    public String getClassDesc() {
        return classDesc;
    }
}
