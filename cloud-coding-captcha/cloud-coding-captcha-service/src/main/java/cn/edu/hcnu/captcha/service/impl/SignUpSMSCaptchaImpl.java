package cn.edu.hcnu.captcha.service.impl;

import cn.edu.hcnu.captcha.config.SMSConfig;
import cn.edu.hcnu.captcha.service.SMSCaptcha;

/**
 * @ClassName SignUpSMSCaptchaImpl
 * @Description TODO
 * @Author liang
 * @Date 2023/6/7 11:23
 * @Version 1.0
 **/

public class SignUpSMSCaptchaImpl extends SMSCaptcha {
    public SignUpSMSCaptchaImpl(String code, SMSConfig smsConfig, String phoneNumber) {
        super(code, smsConfig, phoneNumber);
    }
}
