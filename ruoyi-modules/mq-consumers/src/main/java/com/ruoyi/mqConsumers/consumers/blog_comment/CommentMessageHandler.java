package com.ruoyi.mqConsumers.consumers.blog_comment;

import com.ruoyi.common.mq.domain.BlogCommentMessage;
import com.ruoyi.mqConsumers.service.BlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CommentMessageHandler {

    @Resource
    private BlogService blogService;

    private static final Logger log = LoggerFactory.getLogger(CommentMessageHandler.class);

    public void handle(BlogCommentMessage message) {
        Long blogId = message.getBlogId();
        int i = blogService.addCommentCnt(blogId);
        if (i <= 0) {
            log.error("数据库自增失败！message:{}", message);
        }
    }

}
