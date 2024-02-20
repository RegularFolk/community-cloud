package com.ruoyi.mqConsumers.consumers.blog_collect;

import com.ruoyi.common.mq.constants.MqConsumerGroupConstants;
import com.ruoyi.common.mq.constants.MqTopicConstants;
import com.ruoyi.common.mq.domain.BlogCollectMessage;
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
        topic = MqTopicConstants.COLLECT_TOPIC,
        consumerGroup = MqConsumerGroupConstants.COLLECT_GROUP,
        consumeMode = ConsumeMode.ORDERLY,
        messageModel = MessageModel.CLUSTERING)
public class CollectConsumer_2 implements RocketMQListener<BlogCollectMessage>, RocketMQPushConsumerLifecycleListener {

    @Resource
    private CollectHandler handler;

    private static final Logger log = LoggerFactory.getLogger(CollectConsumer_2.class);

    @Override
    public void onMessage(BlogCollectMessage message) {
        log.info("CollectConsumer_2 收到消息，message = {}", message);
        handler.handle(message);
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        consumer.setInstanceName("CollectConsumer_2");
    }
}
