package cc.cloudcoding.forum;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/15 20:03
 */

@SpringBootApplication
@MapperScan("cc.cloudcoding.forum.*.mapper")
@ComponentScan(basePackages = {"cc.cloudcoding"})
public class CloudCodingForumApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudCodingForumApplication.class,args);
    }
}
