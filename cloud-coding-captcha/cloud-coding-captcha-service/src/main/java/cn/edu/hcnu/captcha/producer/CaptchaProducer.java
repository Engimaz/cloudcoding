package cn.edu.hcnu.captcha.producer;

public interface CaptchaProducer<T> {
    void send(T dto);
}
