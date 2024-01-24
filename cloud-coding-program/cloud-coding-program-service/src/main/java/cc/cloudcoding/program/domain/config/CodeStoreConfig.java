package cc.cloudcoding.program.domain.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 代码存储配置
 *
 * @author 不才小马  邮箱:Aichen517@qq.com
 * @date 2023/07/01
 */
@Data
@Configuration
//@ConfigurationProperties(prefix = "cloud-coding.code-store")
public class CodeStoreConfig {
    @Value("${cloud-coding.code-store.local-root-path}")
    String localRootPath;
    @Value("${cloud-coding.code-store.container-root-path}")
    String containerRootPath;
}

