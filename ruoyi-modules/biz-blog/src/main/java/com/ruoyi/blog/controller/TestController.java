package com.ruoyi.blog.controller;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private RocketMQTemplate rocketmqTemplate;

    @GetMapping("/test")
    public  String test() {
        Message<String> msg = MessageBuilder.withPayload("Hello,RocketMQ").build();
        rocketmqTemplate.send("testTopic", msg);
        return "快去查看控制台！";
    }

}
