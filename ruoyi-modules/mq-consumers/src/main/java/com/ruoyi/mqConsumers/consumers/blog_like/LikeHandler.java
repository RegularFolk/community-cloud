package com.ruoyi.mqConsumers.consumers.blog_like;

import com.ruoyi.common.mq.domain.BlogLikeMessage;
import com.ruoyi.common.mq.enums.OperateType;
import com.ruoyi.mqConsumers.service.BlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class LikeHandler {

    @Resource
    private BlogService blogService;

    private static final Logger log = LoggerFactory.getLogger(LikeHandler.class);

    public void handle(BlogLikeMessage message) {
        Long blogId = message.getBlogId();
        OperateType typeEnum = OperateType.getEnum(message.getOperateType());

        int flag = blogService.changeLikeCnt(blogId, typeEnum);

        if (flag <= 0) {
            log.error("点赞量更新失败！");
        }

    }

}
