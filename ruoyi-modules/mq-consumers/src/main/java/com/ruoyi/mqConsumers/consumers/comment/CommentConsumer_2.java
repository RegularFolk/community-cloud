package com.ruoyi.mqConsumers.consumers.comment;

import com.ruoyi.common.mq.constants.MqConsumerGroupConstants;
import com.ruoyi.common.mq.constants.MqTopicConstants;
import com.ruoyi.common.mq.domain.CommentMessage;
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
        topic = MqTopicConstants.COMMENT_TOPIC,
        consumerGroup = MqConsumerGroupConstants.COMMENT_GROUP,
        consumeMode = ConsumeMode.ORDERLY,
        messageModel = MessageModel.CLUSTERING)
public class CommentConsumer_2 implements RocketMQListener<CommentMessage>, RocketMQPushConsumerLifecycleListener {

    @Resource
    private CommentHandler handler;

    private static final Logger log = LoggerFactory.getLogger(CommentConsumer_2.class);

    @Override
    public void onMessage(CommentMessage message) {
        log.info("CommentConsumer_2 收到消息，message = {}", message);
        handler.handle(message);
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        consumer.setInstanceName("CommentConsumer_2");
    }
}
