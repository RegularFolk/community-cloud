package com.ruoyi.mqConsumers.consumers.user;

import com.ruoyi.common.mq.constants.MqConsumerGroupConstants;
import com.ruoyi.common.mq.constants.MqTopicConstants;
import com.ruoyi.common.mq.domain.UserMessage;
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
        topic = MqTopicConstants.USER_TOPIC,
        consumerGroup = MqConsumerGroupConstants.USER_GROUP,
        consumeMode = ConsumeMode.ORDERLY,
        messageModel = MessageModel.CLUSTERING)
public class UserConsumer_1 implements RocketMQListener<UserMessage>, RocketMQPushConsumerLifecycleListener {

    @Resource
    private UserHandler handler;

    private static final Logger log = LoggerFactory.getLogger(UserConsumer_1.class);


    @Override
    public void onMessage(UserMessage message) {
        log.info("UserConsumer_1 收到消息，message = {}", message);
        handler.handle(message);
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        consumer.setInstanceName("UserConsumer_1");
    }
}
