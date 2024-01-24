package cc.cloudcoding.captcha.controller;

import cc.cloudcoding.base.model.RestResponse;
import cc.cloudcoding.captcha.model.PhoneDTO;
import cc.cloudcoding.captcha.producer.CaptchaProducer;
import cc.cloudcoding.captcha.rpc.GeneratorCaptcha;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/6/6 9:59
 */
@RestController
@Slf4j
@RequestMapping("/sms")
@Tag(name = "手机验证码模块")
public class SmsCaptchaController {
    @Autowired
    @Qualifier("smsCaptchaProducer")
    private CaptchaProducer<PhoneDTO> smsCaptchaProducer;

    @Autowired
    @Qualifier("digitCaptcha")
    private GeneratorCaptcha generatorCaptcha;


    @PostMapping("send")
    @Operation(summary = "发送手机验证码")
    RestResponse<String> send(@RequestBody @Valid PhoneDTO dto) {
        String code = generatorCaptcha.getCode(4);
        dto.setCode(code);
        smsCaptchaProducer.send(dto);
        return new RestResponse<>(200, "发送短信成功", code);
    }
}
