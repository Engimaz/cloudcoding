package cc.cloudcoding.manager;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/6 23:08
 */
@SpringBootApplication
@MapperScan("cc.cloudcoding.manager.*.mapper")
@ComponentScan(basePackages = {"cc.cloudcoding"})
public class CloudCodingManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudCodingManagerApplication.class,args);
    }
}
