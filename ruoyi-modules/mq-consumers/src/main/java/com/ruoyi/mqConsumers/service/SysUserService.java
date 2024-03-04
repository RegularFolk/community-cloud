package com.ruoyi.mqConsumers.service;

import com.ruoyi.common.mq.enums.OperateType;

public interface SysUserService {
    int changeFollowCnt(Long userId, OperateType typeEnum);
}
