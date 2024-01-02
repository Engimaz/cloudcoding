package cn.edu.hcnu.captcha.service.impl;

import cn.edu.hcnu.captcha.rpc.GeneratorCaptcha;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/6/5 23:23
 */
@Service("letterCaptcha")
@Slf4j
public class LetterCaptcha implements GeneratorCaptcha {
    @Override
    public String getCode(int len) {
        return RandomUtil.randomString(len);
    }
}
