package cn.edu.hcnu.captcha.validate;

import cn.edu.hcnu.captcha.annotation.PhoneNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/6/6 10:07
 */
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        if (phone == null) {
            return false;
        }
        // 手机号校验规则
        String regExp = "^1[3456789]\\d{9}$";
        return Pattern.matches(regExp, phone);
    }
}