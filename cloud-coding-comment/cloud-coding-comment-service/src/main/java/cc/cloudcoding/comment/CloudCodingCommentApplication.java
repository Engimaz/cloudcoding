package cc.cloudcoding.comment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("cc.cloudcoding.comment.*.mapper")
@ComponentScan(basePackages = {"cc.cloudcoding"})
public class CloudCodingCommentApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudCodingCommentApplication.class, args);
    }
}
