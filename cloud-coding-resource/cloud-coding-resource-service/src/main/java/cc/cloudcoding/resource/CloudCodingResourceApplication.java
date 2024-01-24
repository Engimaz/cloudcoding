package cc.cloudcoding.resource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("cc.cloudcoding.resource.*.mapper")
@ComponentScan(basePackages = {"cc.cloudcoding"})
public class CloudCodingResourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudCodingResourceApplication.class, args);
    }
}
