package cc.cloudcoding.dictionary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/13 23:09
 */
@SpringBootApplication
@ComponentScan(basePackages = {"cc.cloudcoding"})
public class CloudCodingDictionaryApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudCodingDictionaryApplication.class, args);
    }
}
