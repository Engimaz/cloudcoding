package cn.edu.hcnu.captcha.service;

import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;

/**
 * @description: 注册邮箱验证码
 * @author: Administrator
 * @time: 2023/6/5 22:55
 */
@Slf4j
@Setter
@Accessors(chain = true)
public abstract class EmailCaptcha extends AbstractCaptcha {


    /**
     * 发送邮件
     */
    private JavaMailSender mailSender;

    /**
     * 邮箱模板
     */
    private TemplateEngine templateEngine;


    /*
     *  html 模板文件名
     */
    private String templateName;

    /*
     * 接收方
     */
    private String receiver;
    /*
     * 发送方邮箱
     */
    private String sender;

    private String subject;

    public EmailCaptcha(String code, JavaMailSender mailSender, TemplateEngine templateEngine,
                        String templateName, String receiver, String sender, String subject
    ) {
        super(code);
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.templateName = templateName;
        this.receiver = receiver;
        this.sender = sender;
        this.subject = subject;
    }

    @Override
    public boolean sendMessage() {

        // 存入缓存 验证码两分钟过期

        //创建模板上下文 这个会在生成 html 渲染时使用
        Context context = new Context();
        context.setVariable("code", Arrays.asList(this.code.split("")));

        //将模块引擎内容解析成html字符串
        String emailContent = templateEngine.process(templateName, context);

        MimeMessage message = mailSender.createMimeMessage();

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            // 设置发件人
            helper.setFrom(sender + "(云上编程团队)");
            // 设置收件人
            helper.setTo(receiver);
            // 设置主体
            helper.setSubject(subject);
            // 设置正文
            helper.setText(emailContent, true);
            mailSender.send(message);


            return true;


        } catch (MessagingException e) {
            log.error("获取邮箱验证码时出现异常");
            return false;

        }
    }
}
