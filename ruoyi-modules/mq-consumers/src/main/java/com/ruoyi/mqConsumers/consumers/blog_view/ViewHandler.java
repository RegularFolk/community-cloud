package com.ruoyi.mqConsumers.consumers.blog_view;


import com.ruoyi.common.mq.domain.blog.ViewMessage;
import com.ruoyi.mqConsumers.service.BlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ViewHandler {

    @Resource
    private BlogService blogService;

    private static final Logger log = LoggerFactory.getLogger(ViewHandler.class);

    public void handle(ViewMessage message) {
        Long blogId = message.getBlogId();
        int flag = blogService.addViewCnt(blogId);

        if (flag <= 0) {
            log.error("浏览量自增失败！");
        }

    }
}
