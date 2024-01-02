package cn.edu.hcnu.captcha.service;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/6/5 22:53
 */
public abstract class AbstractCaptcha {
    protected String code;

    public abstract boolean sendMessage();

    public AbstractCaptcha(String code){
        this.code=code;
    }
}
