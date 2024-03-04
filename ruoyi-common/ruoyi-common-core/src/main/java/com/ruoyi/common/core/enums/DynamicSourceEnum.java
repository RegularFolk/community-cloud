package com.ruoyi.common.core.enums;

/**
 * 动态数据源配置
 */
public enum DynamicSourceEnum {
    MASTER("master", "主库（默认）"),
    SLAVE("slave", "从库");

    private final String dataSource;

    private final String dataSourceDesc;

    public String getDataSource() {
        return dataSource;
    }

    public String getDataSourceDesc() {
        return dataSourceDesc;
    }

    DynamicSourceEnum(String dataSource, String dataSourceDesc) {
        this.dataSource = dataSource;
        this.dataSourceDesc = dataSourceDesc;
    }
}
