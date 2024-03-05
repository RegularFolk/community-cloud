package com.ruoyi.mqConsumers.consumers.blog;

import com.ruoyi.common.mq.domain.BlogMessage;
import com.ruoyi.common.mq.enums.OperateType;
import com.ruoyi.mqConsumers.service.BlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class BlogHandler {

    @Resource
    private BlogService blogService;

    private static final Logger log = LoggerFactory.getLogger(BlogHandler.class);

    public void handle(BlogMessage message) {
        Long blogId = message.getBlogId();
        OperateType operateType = OperateType.getEnum(message.getOperateType());
        BlogMessage.MessageType messageType = BlogMessage.MessageType.getEnum(message.getType());

        int flag = blogService.handleBlogMessage(blogId, operateType, messageType);

        if (flag <= 0) {
            log.error("消息消费失败！ message = {}", message);
        }
    }
}
