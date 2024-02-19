package com.ruoyi.mqConsumers.consumers.blog_comment;

import com.ruoyi.common.mq.constants.MqConsumerGroupConstants;
import com.ruoyi.common.mq.constants.MqTopicConstants;
import com.ruoyi.common.mq.domain.BlogCommentMessage;
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
        topic = MqTopicConstants.COMMENT_TOPIC,
        consumerGroup = MqConsumerGroupConstants.COMMENT_GROUP,
        consumeMode = ConsumeMode.ORDERLY,
        messageModel = MessageModel.CLUSTERING)
public class CommentConsumer_2 implements RocketMQListener<BlogCommentMessage>, RocketMQPushConsumerLifecycleListener {

    @Resource
    CommentMessageHandler handler;

    @Override
    public void onMessage(BlogCommentMessage message) {
        System.out.println("CommentConsumer_2 收到消息，message  = " + message);
        handler.handle(message);
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        consumer.setInstanceName("CommentConsumer_2");
    }
}
