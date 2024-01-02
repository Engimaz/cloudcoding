package cn.edu.hcnu.captcha.consumer;

import cn.edu.hcnu.captcha.config.SMSConfig;
import cn.edu.hcnu.captcha.design.CaptchaFactory;
import cn.edu.hcnu.captcha.design.impl.SignUpSMSCaptchaFactory;
import cn.edu.hcnu.captcha.model.PhoneDTO;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/6/5 23:55
 */
@Component
@RocketMQMessageListener(
        consumerGroup = "cloud-coding-sms-consumer",
        topic = "captcha",
        selectorExpression = "sms"
)
@Slf4j
public class SMSCaptchaConsumer implements RocketMQListener<MessageExt> {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private ApplicationContext context;


    @Override
    public void onMessage(MessageExt s) {

        String tags = s.getTags();

        if ("sms".equals(tags)) {
            PhoneDTO dto = JSON.parseObject(new String(s.getBody(), StandardCharsets.UTF_8), PhoneDTO.class);


            CaptchaFactory captchaFactory = new SignUpSMSCaptchaFactory(
                    dto.getCode(),
                    context.getBean(SMSConfig.class),
                    dto.getReceiver()
            );
            boolean send = captchaFactory.createCaptcha().sendMessage();

            if (send) {
                // 设置一分钟过期
                redisTemplate.opsForValue().set(dto.getType()+":"+dto.getReceiver(), dto.getCode(), 1, TimeUnit.MINUTES);

                log.info("{} 的短信成功发送", dto.getReceiver());
            } else {
                log.error("{} 的短信发送失败", dto.getReceiver());
            }
        }else {
            log.error("无效的短信标签");
        }

    }

}
