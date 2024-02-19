package com.ruoyi.mqConsumers.consumers.blog_like;

import com.ruoyi.common.mq.domain.BlogLikeMessage;
import com.ruoyi.mqConsumers.service.BlogService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class LikeMessageHandler {

    @Resource
    private BlogService blogService;

    public void handle(BlogLikeMessage message) {

    }

}
