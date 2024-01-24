package cc.cloudcoding.captcha.validate;

/**
 * @description:
 * @author: Administrator
 * @time: 2022/12/23 21:01
 */
public interface EnumValidate<T> {
    boolean existValidate(T val);
}
