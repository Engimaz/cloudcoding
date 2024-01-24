package cc.cloudcoding.captcha.producer;

public interface CaptchaProducer<T> {
    void send(T dto);
}
