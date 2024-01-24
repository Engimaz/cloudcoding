package cc.cloudcoding.captcha.model;

import cc.cloudcoding.captcha.validate.EnumValidate;
import cn.hutool.core.util.StrUtil;
public enum CodeType implements EnumValidate<String> {

    SignUp("sign-up-code", "注册验证码"),
    Reset("reset-code", "重置验证码");

    private final String key;
    private final String value;

    private CodeType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean existValidate(String val) {
        if (StrUtil.isBlank(val)) {
            return false;
        }

        for (CodeType value : CodeType.values()) {
            if (value.getKey().equals(val)) {
                return true;
            }
        }

        return false;
    }
}
