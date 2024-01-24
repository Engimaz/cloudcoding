package cc.cloudcoding.captcha.service.impl;

import cc.cloudcoding.captcha.rpc.GeneratorCaptcha;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/6/5 23:22
 */
@Service("digitCaptcha")
@Slf4j
public class DigitCaptcha implements GeneratorCaptcha {

    @Override
    public String getCode(int len) {
        return RandomUtil.randomNumbers(len);
    }
}
