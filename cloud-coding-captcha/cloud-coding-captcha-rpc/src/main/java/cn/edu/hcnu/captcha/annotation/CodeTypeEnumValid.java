package cn.edu.hcnu.captcha.annotation;

import cn.edu.hcnu.captcha.validate.EmailTypeEnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


// 在哪个生命周期使用
@Retention(RetentionPolicy.RUNTIME)
// 这个注解可以标注在那个方法上 field 属性 method 方法
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
// 限定类
@Constraint(validatedBy = {EmailTypeEnumValidator.class})
// 生成注解文档
@Documented
public @interface CodeTypeEnumValid {

    // 相当与 class 中的属性
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?>[] target() default {};

    /**
     * 允许的枚举
     */

    Class<? extends Enum<?>> enumClass();

}
