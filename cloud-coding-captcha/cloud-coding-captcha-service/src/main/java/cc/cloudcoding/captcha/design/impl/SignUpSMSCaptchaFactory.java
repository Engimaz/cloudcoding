package cc.cloudcoding.captcha.design.impl;

import cc.cloudcoding.captcha.service.AbstractCaptcha;
import cc.cloudcoding.captcha.service.impl.SignUpSMSCaptchaImpl;
import cc.cloudcoding.captcha.config.SMSConfig;
import cc.cloudcoding.captcha.design.CaptchaFactory;

/**
 * @ClassName SignUpPSMSCaptchaFactory
 * @Description TODO
 * @Author liang
 * @Date 2023/6/7 11:21
 * @Version 1.0
 **/

public class SignUpSMSCaptchaFactory implements CaptchaFactory {

    private String code;

    private SMSConfig smsConfig;

    private String phoneNumber;

    public SignUpSMSCaptchaFactory(String code, SMSConfig smsConfig, String phoneNumber) {
        this.code = code;
        this.smsConfig = smsConfig;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public AbstractCaptcha createCaptcha() {
        return new SignUpSMSCaptchaImpl(code, smsConfig, phoneNumber);
    }
}
