package com.ruoyi.mqConsumers.service;

import com.ruoyi.common.mq.domain.UserMessage;
import com.ruoyi.common.mq.enums.OperateType;

public interface SysUserService {
    int changeFollowCnt(Long userId, OperateType typeEnum);

    int handleUserMessage(Long userId, OperateType operateType, UserMessage.MessageType messageType);
}
