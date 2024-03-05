package com.ruoyi.mqConsumers.consumers.user;

import com.ruoyi.common.mq.domain.UserMessage;
import com.ruoyi.common.mq.enums.OperateType;
import com.ruoyi.mqConsumers.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UserHandler {
    @Resource
    private SysUserService userService;

    private static final Logger log = LoggerFactory.getLogger(UserHandler.class);

    public void handle(UserMessage message) {
        Long userId = message.getUserId();
        OperateType operateType = OperateType.getEnum(message.getType());
        UserMessage.MessageType messageType = UserMessage.MessageType.getEnum(message.getType());

        int flag = userService.handleUserMessage(userId, operateType, messageType);

        if (flag <= 0) {
            log.error("消息消费失败！ message = {}", message);
        }

    }
}
