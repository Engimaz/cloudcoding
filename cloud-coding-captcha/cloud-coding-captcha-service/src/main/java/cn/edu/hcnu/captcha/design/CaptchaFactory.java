package cn.edu.hcnu.captcha.design;


import cn.edu.hcnu.captcha.service.AbstractCaptcha;

/**
 * @author AICHEN
 */
public interface CaptchaFactory {
    AbstractCaptcha createCaptcha();
}
