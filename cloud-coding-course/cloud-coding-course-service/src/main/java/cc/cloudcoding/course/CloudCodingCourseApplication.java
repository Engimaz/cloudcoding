package cc.cloudcoding.course;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("cc.cloudcoding.course.*.mapper")
@ComponentScan(basePackages = {"cc.cloudcoding"})
public class CloudCodingCourseApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudCodingCourseApplication.class,args);
    }
}
