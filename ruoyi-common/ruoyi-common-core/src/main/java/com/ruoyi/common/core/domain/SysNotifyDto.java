package com.ruoyi.common.core.domain;

import java.util.List;

public class SysNotifyDto {

    private List<Long> userIds;

    private String content;

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SysNotifyDto{" +
                "userIds=" + userIds +
                ", content='" + content + '\'' +
                '}';
    }
}
