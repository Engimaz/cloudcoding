package cn.edu.hcnu.captcha.controller;

import cn.edu.hcnu.base.model.RestResponse;
import cn.edu.hcnu.captcha.model.EmailDTO;
import cn.edu.hcnu.captcha.producer.impl.EmailCaptchaProducer;
import cn.edu.hcnu.captcha.rpc.GeneratorCaptcha;
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
 * @time: 2023/6/5 23:34
 */
@RestController
@Slf4j
@RequestMapping("/email")
@Tag(name = "邮箱验证码模块")
public class EmailCaptchaController {


    @Autowired
    private EmailCaptchaProducer emailCaptchaProducer;

    @Autowired
    @Qualifier("letterCaptcha")
    private GeneratorCaptcha generatorCaptcha;



    @PostMapping("send")
    @Operation(summary = "发送邮箱验证码")
    RestResponse<String> send(@RequestBody @Valid EmailDTO dto) {
        String code = generatorCaptcha.getCode(4);
        dto.setCode(code);
        emailCaptchaProducer.send(dto);
        return new RestResponse<>(200, "发送邮件成功", code);
    }



}
