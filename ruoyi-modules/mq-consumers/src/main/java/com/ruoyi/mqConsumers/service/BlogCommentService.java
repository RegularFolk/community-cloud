package com.ruoyi.mqConsumers.service;

import com.ruoyi.common.mq.enums.OperateType;

public interface BlogCommentService {
    int changeLikeCnt(Long commentId, OperateType typeEnum);
}
