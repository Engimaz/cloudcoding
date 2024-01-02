package cn.edu.hcnu.program.domain.service.code.cpp;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


/**
 * cpp文件配置
 *
 * @author 不才小马  邮箱:Aichen517@qq.com
 * @date 2023/07/01
 */
@Configuration
//@ConfigurationProperties(prefix = "cloud-coding.cpp")
@Data
public class CPPConfig {
    @Value("${cloud-coding.cpp.run-command}")
    private String runCommand;

    @Value("${cloud-coding.cpp.image-name}")
    private String imageName;
}
