package com.ruoyi.mqConsumers.consumers.system;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(
        topic = "RMQ_SYS_TRACE_TOPIC",
        consumerGroup = "RMQ_SYS_TRACE_CONSUMERS",
        messageModel = MessageModel.CLUSTERING)
public class SysTraceConsumer implements RocketMQListener<String>, RocketMQPushConsumerLifecycleListener {

    /*
    * RocketMQ 默认开启消息追踪，以消息追踪队列的形式，每次发送消息都会再发送一个追踪消息
    * 设置一个消费者监听一下这个主题，消费一下消息
    * */

    private static final Logger log = LoggerFactory.getLogger(SysTraceConsumer.class);

    @Override
    public void onMessage(String message) {
        log.info("收到 SYS_TRACE 消息，message = {}", message);
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        consumer.setInstanceName("SysTraceConsumer");
    }
}
