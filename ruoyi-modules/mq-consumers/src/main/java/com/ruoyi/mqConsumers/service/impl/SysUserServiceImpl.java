package com.ruoyi.mqConsumers.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.common.mq.domain.UserMessage;
import com.ruoyi.common.mq.enums.OperateType;
import com.ruoyi.mqConsumers.mapper.SysUserMapper;
import com.ruoyi.mqConsumers.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@DS("slave")
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper userMapper;

    @Override
    public int changeFollowCnt(Long userId, OperateType typeEnum) {
        int flag = 0;
        switch (typeEnum) {
            case ADD:
                flag = userMapper.increaseFollowCnt(userId);
                break;
            case CANCEL:
                flag = userMapper.decreaseFollowCnt(userId);
                break;
            default:
                // unreachable
                break;
        }

        return flag;
    }

    @Override
    public int handleUserMessage(Long userId, OperateType operateType, UserMessage.MessageType messageType) {
        int flag = 0;

        switch (messageType) {
            case FOLLOW:
                flag = userMapper.changeFollowCnt(userId, operateType.getType());
                break;
            case LIKE:
                flag = userMapper.changeLikeCnt(userId, operateType.getType());
                break;
            default:
                // unreachable
                break;
        }


        return flag;
    }
}
