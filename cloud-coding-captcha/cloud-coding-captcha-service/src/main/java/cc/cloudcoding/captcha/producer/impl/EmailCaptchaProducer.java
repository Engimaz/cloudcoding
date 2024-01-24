package cc.cloudcoding.captcha.producer.impl;

import cc.cloudcoding.captcha.model.EmailDTO;
import cc.cloudcoding.captcha.producer.CaptchaProducer;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/6/5 21:42
 */
@Component("emailCaptchaProducer")
@Slf4j
public class EmailCaptchaProducer implements CaptchaProducer<EmailDTO> {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void send(EmailDTO dto) {
        String s = JSON.toJSONString(dto);
        log.info("发送邮件进队列{}", s);
        rocketMQTemplate.syncSend("captcha:email", s);
    }
}

