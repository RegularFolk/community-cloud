package com.ruoyi.mqConsumers.consumers.blog_collect;

import com.ruoyi.common.mq.domain.blog.CollectMessage;
import com.ruoyi.common.mq.enums.OperateType;
import com.ruoyi.mqConsumers.service.BlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CollectHandler {

    @Resource
    private BlogService blogService;

    private static final Logger log = LoggerFactory.getLogger(CollectHandler.class);

    public void handle(CollectMessage message) {
        Long blogId = message.getBlogId();
        OperateType typeEnum = message.getOperateType();

        int flag = blogService.changeCollectCnt(blogId, typeEnum);

        if (flag <= 0) {
            log.error("收藏量改变失败！");
        }

    }

}
