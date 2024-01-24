package cc.cloudcoding.captcha.service.impl;

import cc.cloudcoding.captcha.config.SMSConfig;
import cc.cloudcoding.captcha.service.AbstractCaptcha;
import com.alibaba.fastjson.JSON;
import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import darabonba.core.client.ClientOverrideConfiguration;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/6/6 10:29
 */

@Slf4j
public class SmsCaptchaImpl extends AbstractCaptcha {


    private SMSConfig smsConfig;


    // 电话号码
    private String phoneNumber;


    public SmsCaptchaImpl(SMSConfig smsConfig, String phoneNumber, String code) {
        super(code);
        this.smsConfig = smsConfig;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean sendMessage() {

        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(smsConfig.getAccessKeyId())
                .accessKeySecret(smsConfig.getAccessKeySecret())
                .build());
        AsyncClient client = AsyncClient.builder()
                .region(smsConfig.getRegionID())
                .credentialsProvider(provider)
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride(smsConfig.getEndpoint())
                )
                .build();

        SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                .phoneNumbers(phoneNumber)
                .signName(smsConfig.getSignName())
                .templateCode(smsConfig.getTemplateCode())
                .templateParam("{\"code\":\"" + this.code + "\"}")
                .build();

        CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);
        try {
            SendSmsResponse resp = response.get();
            String s = JSON.toJSONString(resp);
            log.info("{}", s);

            return true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

    }
}
