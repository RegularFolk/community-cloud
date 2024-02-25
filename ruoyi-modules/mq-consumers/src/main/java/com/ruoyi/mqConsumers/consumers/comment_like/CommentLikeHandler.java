package com.ruoyi.mqConsumers.consumers.comment_like;

import com.ruoyi.common.mq.domain.blog.CommentLikeMessage;
import com.ruoyi.common.mq.enums.OperateType;
import com.ruoyi.mqConsumers.service.BlogCommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CommentLikeHandler {

    @Resource
    private BlogCommentService blogCommentService;

    private static final Logger log = LoggerFactory.getLogger(CommentLikeHandler.class);

    public void handle(CommentLikeMessage message) {
        Long commentId = message.getCommentId();
        OperateType typeEnum = OperateType.getEnum(message.getOperateType());

        int flag = blogCommentService.changeLikeCnt(commentId, typeEnum);

        if (flag <= 0) {
            log.error("点赞量更新失败！");
        }

    }

}
