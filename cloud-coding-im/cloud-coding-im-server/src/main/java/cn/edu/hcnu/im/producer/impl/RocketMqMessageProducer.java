package cn.edu.hcnu.im.producer.impl;

import cn.edu.hcnu.im.producer.IMessageProducer;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/28 11:38
 */

@Component
public class RocketMqMessageProducer<T> implements IMessageProducer<T> {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void sendMessage(String key, T message) {
        rocketMQTemplate.syncSend(key, message);

    }
}
