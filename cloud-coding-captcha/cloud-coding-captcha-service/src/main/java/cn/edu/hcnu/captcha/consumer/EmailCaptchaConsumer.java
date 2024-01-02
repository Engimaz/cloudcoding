package cn.edu.hcnu.captcha.consumer;

import cn.edu.hcnu.captcha.design.CaptchaFactory;
import cn.edu.hcnu.captcha.design.impl.SignUpEmailCaptchaFactory;
import cn.edu.hcnu.captcha.model.EmailDTO;
import cn.edu.hcnu.captcha.model.CodeType;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/6/5 23:55
 */
@Component
@RocketMQMessageListener(
        consumerGroup = "cloud-coding-email-consumer",
        topic = "captcha",
        selectorExpression = "email"
)
@Slf4j
public class EmailCaptchaConsumer implements RocketMQListener<MessageExt> {


    @Autowired
    private ApplicationContext context;


    @Value("${spring.mail.userName}")
    private String sender;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void onMessage(MessageExt s) {

        String tags = s.getTags();
        log.info("正常处理消息,{}", s);

        if ("email".equals(tags)) {
            EmailDTO dto = JSON.parseObject(new String(s.getBody()), EmailDTO.class);

            if (dto.getType().equals(CodeType.SignUp.getKey())) {
                CaptchaFactory captchaFactory = new SignUpEmailCaptchaFactory(
                        dto.getCode(),
                        context.getBean(JavaMailSender.class),
                        context.getBean(TemplateEngine.class),
                        "RegisterCode",
                        dto.getReceiver(),
                        this.sender,
                        "注册验证码"
                );
                // 抽象调用方法
                boolean b = captchaFactory.createCaptcha().sendMessage();
                if (b) {
                    log.info("{} 的邮件成功发送", dto.getReceiver());
                    // 设置一分钟过期
                    redisTemplate.opsForValue().set(dto.getType() + ":" + dto.getReceiver(), dto.getCode(), 1, TimeUnit.MINUTES);

                } else {
                    log.error("{} 的邮件发送失败", dto.getReceiver());
                }
            }else if(dto.getType().equals(CodeType.Reset.getKey())){
                CaptchaFactory captchaFactory = new SignUpEmailCaptchaFactory(
                        dto.getCode(),
                        context.getBean(JavaMailSender.class),
                        context.getBean(TemplateEngine.class),
                        "ResetCode",
                        dto.getReceiver(),
                        this.sender,
                        "重置验证码"
                );
                // 抽象调用方法
                boolean b = captchaFactory.createCaptcha().sendMessage();
                if (b) {
                    log.info("{} 的邮件成功发送", dto.getReceiver());
                    // 设置一分钟过期
                    redisTemplate.opsForValue().set(dto.getType() + ":" + dto.getReceiver(), dto.getCode(), 1, TimeUnit.MINUTES);

                } else {
                    log.error("{} 的邮件发送失败", dto.getReceiver());
                }
            }
        }


    }

}
