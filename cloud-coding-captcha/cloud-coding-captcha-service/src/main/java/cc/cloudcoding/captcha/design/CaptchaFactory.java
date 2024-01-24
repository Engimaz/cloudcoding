package cc.cloudcoding.captcha.design;


import cc.cloudcoding.captcha.service.AbstractCaptcha;

/**
 * @author AICHEN
 */
public interface CaptchaFactory {
    AbstractCaptcha createCaptcha();
}
