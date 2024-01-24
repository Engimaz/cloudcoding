package cc.cloudcoding.captcha.design.impl;

import cc.cloudcoding.captcha.service.AbstractCaptcha;
import cc.cloudcoding.captcha.service.impl.SignEmailCaptchaImpl;
import cc.cloudcoding.captcha.design.CaptchaFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;

/**
 * @ClassName EmailCaptchaFactory
 * @Description TODO
 * @Author liang
 * @Date 2023/6/7 10:40
 * @Version 1.0
 **/

public class SignUpEmailCaptchaFactory implements CaptchaFactory {

    private String code;
    private JavaMailSender mailSender;
    private TemplateEngine templateEngine;
    private String templateName;
    private String receiver;
    private String sender;
    private String subject;

    public SignUpEmailCaptchaFactory(String code,
                                     JavaMailSender mailSender,
                                     TemplateEngine templateEngine,
                                     String templateName,
                                     String receiver,
                                     String sender,
                                     String subject) {
        this.code = code;
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.templateName = templateName;
        this.receiver = receiver;
        this.sender = sender;
        this.subject = subject;
    }

    @Override
    public AbstractCaptcha createCaptcha() {
        return new SignEmailCaptchaImpl(code, mailSender, templateEngine, templateName, receiver, sender, subject);
    }
}
