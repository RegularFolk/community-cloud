package com.ruoyi.mqConsumers.service;

import com.ruoyi.common.mq.domain.CommentMessage;
import com.ruoyi.common.mq.enums.OperateType;

public interface BlogCommentService {
    int changeLikeCnt(Long commentId, OperateType typeEnum);

    int handleCommentMessage(Long commentId, OperateType operateType, CommentMessage.MessageType messageType);
}
