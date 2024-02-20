package com.ruoyi.common.mq.callBack;

import com.ruoyi.common.mq.domain.BaseMessage;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultCallBack<T> implements SendCallback {

    private static Logger log;

    private final BaseMessage message;

    public DefaultCallBack(Class<T> targetClass, BaseMessage message) {
        log = LoggerFactory.getLogger(targetClass);
        this.message = message;
    }

    @Override
    public void onSuccess(SendResult sendResult) {
        log.info("消息发送成功！sendResult = {}", sendResult);
    }

    @Override
    public void onException(Throwable throwable) {
        log.error("消息发送失败！message = {}\nerror = {}", this.message, throwable.getMessage());
    }
}
