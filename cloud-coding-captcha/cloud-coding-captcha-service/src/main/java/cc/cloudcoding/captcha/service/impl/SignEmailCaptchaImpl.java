package cc.cloudcoding.captcha.service.impl;

import cc.cloudcoding.captcha.service.EmailCaptcha;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;

/**
 * @ClassName SignEmailCodeCaptchaImpl 登录验证码是实现类
 * @Description TODO
 * @Author liang
 * @Date 2023/6/7 10:54
 * @Version 1.0
 **/

public class SignEmailCaptchaImpl extends EmailCaptcha {


    public SignEmailCaptchaImpl(String code, JavaMailSender mailSender, TemplateEngine templateEngine, String templateName, String receiver, String sender, String subject) {
        super(code, mailSender, templateEngine, templateName, receiver, sender, subject);
    }
}
