package com.ruoyi.mqConsumers.consumers.blog;

import com.ruoyi.common.mq.constants.MqConsumerGroupConstants;
import com.ruoyi.common.mq.constants.MqTopicConstants;
import com.ruoyi.common.mq.domain.BlogMessage;
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
        topic = MqTopicConstants.BLOG_TOPIC,
        consumerGroup = MqConsumerGroupConstants.BLOG_GROUP,
        consumeMode = ConsumeMode.ORDERLY,
        messageModel = MessageModel.CLUSTERING)
public class BLogConsumer_1 implements RocketMQListener<BlogMessage>, RocketMQPushConsumerLifecycleListener {

    @Resource
    private BlogHandler handler;

    private static final Logger log = LoggerFactory.getLogger(BLogConsumer_1.class);

    @Override
    public void onMessage(BlogMessage message) {
        log.info("BLogConsumer_1 收到消息，message = {}", message);
        handler.handle(message);
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        consumer.setInstanceName("BLogConsumer_1");
    }
}
