package cc.cloudcoding.captcha.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/6/6 10:49
 */
@Configuration
@Data
public class SMSConfig {
    @Value("${aliyun.sms.access-key-id}")
    private String accessKeyId;

    @Value("${aliyun.sms.access-key-secret}")
    private String accessKeySecret;

    @Value("${aliyun.sms.sign-name}")
    private String signName;

    @Value("${aliyun.sms.template-code}")
    private String templateCode;

    @Value("${aliyun.sms.endpoint}")
    private String endpoint;

    @Value("${aliyun.sms.region-id}")
    private String regionID;
}
