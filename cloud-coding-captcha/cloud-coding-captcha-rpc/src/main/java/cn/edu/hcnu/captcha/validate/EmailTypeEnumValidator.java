package cn.edu.hcnu.captcha.validate;

import cn.edu.hcnu.captcha.annotation.CodeTypeEnumValid;
import cn.hutool.core.util.StrUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * @description:
 * @author: Administrator
 * @time: 2022/12/23 21:05
 */
public class EmailTypeEnumValidator implements ConstraintValidator<CodeTypeEnumValid,String> {


    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(CodeTypeEnumValid constraintAnnotation) {
        enumClass=constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(String val, ConstraintValidatorContext constraintValidatorContext) {
        if(StrUtil.isBlank(val)) {
            return true;
        }

        EnumValidate[] enums= (EnumValidate[]) enumClass.getEnumConstants();

        if(enums==null || enums.length==0){
            return false;
        }

        return enums[0].existValidate(val);

    }
}
