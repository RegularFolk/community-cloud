package com.ruoyi.mqConsumers.consumers.comment_like;

import com.ruoyi.common.mq.constants.MqConsumerGroupConstants;
import com.ruoyi.common.mq.constants.MqTopicConstants;
import com.ruoyi.common.mq.domain.blog.CommentLikeMessage;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@RocketMQMessageListener(
        topic = MqTopicConstants.COMMENT_LIKE_TOPIC,
        consumerGroup = MqConsumerGroupConstants.COMMENT_LIKE_GROUP,
        consumeMode = ConsumeMode.ORDERLY,
        messageModel = MessageModel.CLUSTERING)
public class CommentLikeConsumer_2 implements RocketMQListener<CommentLikeMessage>, RocketMQPushConsumerLifecycleListener {

    @Resource
    private CommentLikeHandler handler;

    private static final Logger log = LoggerFactory.getLogger(CommentLikeConsumer_2.class);

    @Override
    public void onMessage(CommentLikeMessage message) {
        log.info("CommentLikeConsumer_2 收到消息，message = {}", message);
        handler.handle(message);
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        consumer.setInstanceName("CommentLikeConsumer_2");
    }
}
