package cn.edu.hcnu.program;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 程序应用程序
 *
 * @author 不才小马  邮箱:Aichen517@qq.com
 * @date 2023/07/01
 */
@SpringBootApplication
@ComponentScan({"cn.edu.hcnu"})
@MapperScan("cn.edu.hcnu.program.*.mapper")
public class CloudCodingProgramApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudCodingProgramApplication.class, args);
    }
}
