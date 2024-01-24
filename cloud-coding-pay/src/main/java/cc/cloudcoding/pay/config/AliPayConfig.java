package cc.cloudcoding.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/6/24 19:22
 */
@Component
@Data
@ConfigurationProperties(prefix = "alipay")
public class AliPayConfig {
    private String appid;
    private String private_key;
    private String ali_public_key;
    private String notify_url;
    private String gateway;
    private String charset;
    private String signType;
}
