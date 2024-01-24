package cc.cloudcoding.captcha.service;

import cc.cloudcoding.captcha.config.SMSConfig;
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
 * @ClassName SMSCaptcha
 * @Description TODO
 * @Author liang
 * @Date 2023/6/7 11:17
 * @Version 1.0
 **/

@Slf4j
public abstract class SMSCaptcha extends AbstractCaptcha {

    private SMSConfig smsConfig;

    private String phoneNumber;

    public SMSCaptcha(String code, SMSConfig smsConfig, String phoneNumber) {
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
