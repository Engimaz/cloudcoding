package cc.cloudcoding.resource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("cn.edu.hcnu.resource.*.mapper")
@ComponentScan(basePackages = {"cn.edu.hcnu"})
public class CloudCodingResourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudCodingResourceApplication.class, args);
    }
}
