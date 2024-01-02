package cn.edu.hcnu.captcha.producer.impl;

import cn.edu.hcnu.captcha.model.PhoneDTO;
import cn.edu.hcnu.captcha.producer.CaptchaProducer;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @description:
 * @author: Administrator
 * @time: 2023/6/6 10:11
 */
@Component("smsCaptchaProducer")
@Slf4j
public class SmsCaptchaProducer implements CaptchaProducer<PhoneDTO> {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void send(PhoneDTO dto) {
        String s = JSON.toJSONString(dto, SerializerFeature.WriteNullStringAsEmpty);
        log.info("发送短信进队列{}", s);
//        Message message = new Message("captcha", "sms", s.getBytes(StandardCharsets.UTF_8));
        rocketMQTemplate.syncSend("captcha:sms", s);
    }
}
