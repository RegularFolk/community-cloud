package com.ruoyi.mqConsumers.consumers.blog_like;

import com.ruoyi.common.mq.constants.MqConsumerGroupConstants;
import com.ruoyi.common.mq.constants.MqTopicConstants;
import com.ruoyi.common.mq.domain.BlogLikeMessage;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@RocketMQMessageListener(
        topic = MqTopicConstants.LIKE_TOPIC,
        consumerGroup = MqConsumerGroupConstants.LIKE_GROUP,
        consumeMode = ConsumeMode.ORDERLY,
        messageModel = MessageModel.CLUSTERING)
public class LikeConsumer_1 implements RocketMQListener<BlogLikeMessage>, RocketMQPushConsumerLifecycleListener {

    @Resource
    LikeMessageHandler handler;

    @Override
    public void onMessage(BlogLikeMessage message) {
        System.out.println("consumer_1 : message received!");
        handler.handle(message);
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        consumer.setInstanceName("LikeConsumer_1");
    }
}
