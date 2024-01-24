package cc.cloudcoding.im;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/6/23 13:41
 */
@SpringBootApplication(scanBasePackages = {"cn.edu.hcnu"},exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
public class CloudCodingIMApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudCodingIMApplication.class, args);
    }
}
