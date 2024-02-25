package com.ruoyi.mqConsumers.consumers.blog_like;

import com.ruoyi.common.mq.constants.MqConsumerGroupConstants;
import com.ruoyi.common.mq.constants.MqTopicConstants;
import com.ruoyi.common.mq.domain.blog.LikeMessage;
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
        topic = MqTopicConstants.LIKE_TOPIC,
        consumerGroup = MqConsumerGroupConstants.LIKE_GROUP,
        consumeMode = ConsumeMode.ORDERLY,
        messageModel = MessageModel.CLUSTERING)
public class LikeConsumer_2 implements RocketMQListener<LikeMessage>, RocketMQPushConsumerLifecycleListener {

    @Resource
    private LikeHandler handler;

    private static final Logger log = LoggerFactory.getLogger(LikeConsumer_2.class);

    @Override
    public void onMessage(LikeMessage message) {
        log.info("LikeConsumer_2 收到消息，message = {}", message);
        handler.handle(message);
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        consumer.setInstanceName("LikeConsumer_2");
    }
}
