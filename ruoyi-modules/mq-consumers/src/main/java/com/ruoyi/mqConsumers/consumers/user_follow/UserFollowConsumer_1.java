package com.ruoyi.mqConsumers.consumers.user_follow;

import com.ruoyi.common.mq.constants.MqConsumerGroupConstants;
import com.ruoyi.common.mq.constants.MqTopicConstants;
import com.ruoyi.common.mq.domain.user.UserFollowMessage;
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
        topic = MqTopicConstants.USER_FOLLOW_TOPIC,
        consumerGroup = MqConsumerGroupConstants.USER_FOLLOW_GROUP,
        consumeMode = ConsumeMode.ORDERLY,
        messageModel = MessageModel.CLUSTERING)
public class UserFollowConsumer_1 implements RocketMQListener<UserFollowMessage>, RocketMQPushConsumerLifecycleListener {

    @Resource
    private UserFollowHandler handler;

    private static final Logger log = LoggerFactory.getLogger(UserFollowConsumer_1.class);

    @Override
    public void onMessage(UserFollowMessage message) {
        log.info("UserFollowConsumer_1 收到消息，message = {}", message);
        handler.handle(message);
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        consumer.setInstanceName("UserFollowConsumer_1");
    }
}
