package com.ruoyi.mqConsumers.consumers.user_follow;

import com.ruoyi.common.mq.domain.user.UserFollowMessage;
import com.ruoyi.common.mq.enums.OperateType;
import com.ruoyi.mqConsumers.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UserFollowHandler {

    @Resource
    private SysUserService userService;

    private static final Logger log = LoggerFactory.getLogger(UserFollowHandler.class);

    public void handle(UserFollowMessage message) {
        Long userId = message.getUserId();
        OperateType typeEnum = OperateType.getEnum(message.getOperateType());

        int flag = userService.changeFollowCnt(userId, typeEnum);

        if (flag <= 0) {
            log.error("关注数更新失败！");
        }

    }
}
