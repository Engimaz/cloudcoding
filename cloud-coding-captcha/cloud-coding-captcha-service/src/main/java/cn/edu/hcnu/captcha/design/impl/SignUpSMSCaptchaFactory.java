package cn.edu.hcnu.captcha.design.impl;

import cn.edu.hcnu.captcha.config.SMSConfig;
import cn.edu.hcnu.captcha.design.CaptchaFactory;
import cn.edu.hcnu.captcha.service.AbstractCaptcha;
import cn.edu.hcnu.captcha.service.impl.SignUpSMSCaptchaImpl;

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
