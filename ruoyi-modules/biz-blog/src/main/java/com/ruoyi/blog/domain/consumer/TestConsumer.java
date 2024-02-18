package com.ruoyi.blog.domain.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;


@Component
@RocketMQMessageListener(topic = "testTopic", consumerGroup = "test_consumer_group")
public class TestConsumer implements RocketMQListener<String> {


    @Override
    public void onMessage(String message) {
        System.out.println("MQ测试成功！收到消息了：");
        System.out.println("message = " + message);
    }

}
