package cn.edu.hcnu.course;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("cn.edu.hcnu.course.*.mapper")
@ComponentScan(basePackages = {"cn.edu.hcnu"})
public class CloudCodingCourseApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudCodingCourseApplication.class,args);
    }
}
