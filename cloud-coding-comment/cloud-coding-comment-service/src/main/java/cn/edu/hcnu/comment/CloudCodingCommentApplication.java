package cn.edu.hcnu.comment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("cn.edu.hcnu.comment.*.mapper")
@ComponentScan(basePackages = {"cn.edu.hcnu"})
public class CloudCodingCommentApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudCodingCommentApplication.class, args);
    }
}
