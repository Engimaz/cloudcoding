package cn.edu.hcnu.program.domain.service.code.cpp;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
@Data
public class CPPConfig {
    @Value("${cloud-coding.cpp.run-command}")
    private String runCommand;

    @Value("${cloud-coding.cpp.image-name}")
    private String imageName;
}
