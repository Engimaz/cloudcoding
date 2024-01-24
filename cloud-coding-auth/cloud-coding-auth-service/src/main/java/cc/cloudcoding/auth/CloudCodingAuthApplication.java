package cc.cloudcoding.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author AICHEN
 */
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableDiscoveryClient
@MapperScan("cc.cloudcoding.auth.*.mapper")
public class CloudCodingAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudCodingAuthApplication.class);
    }
}
