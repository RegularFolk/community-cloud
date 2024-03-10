package com.ruoyi.mqConsumers.consumers.comment;

import com.ruoyi.common.mq.domain.CommentMessage;
import com.ruoyi.common.mq.enums.OperateType;
import com.ruoyi.mqConsumers.service.BlogCommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CommentHandler {

    @Resource
    private BlogCommentService commentService;

    private static final Logger log = LoggerFactory.getLogger(CommentHandler.class);

    public void handle(CommentMessage message) {
        Long commentId = message.getCommentId();
        OperateType operateType = OperateType.getEnum(message.getOperateType());
        CommentMessage.MessageType messageType = CommentMessage.MessageType.getEnum(message.getType());

        int flag = commentService.handleCommentMessage(commentId, operateType, messageType);

        if (flag <= 0) {
            log.error("消息消费失败！ message = {}", message);
        }
    }

}
