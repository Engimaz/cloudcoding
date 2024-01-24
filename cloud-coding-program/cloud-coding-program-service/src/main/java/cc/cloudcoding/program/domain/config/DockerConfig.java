package cc.cloudcoding.program.domain.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/6/7 23:28
 */
@Data
@Configuration
public class DockerConfig {

    @Value("${cloud-coding.docker.create-container}")
    private String createContainer;

    @Value("${cloud-coding.docker.enter-container}")
    private String enterContainer;

    @Value("${cloud-coding.docker.search-container}")
    private String searchContainer;

    @Value("${cloud-coding.docker.start-container}")
    private String startContainer;

    @Value("${cloud-coding.docker.stop-container}")
    private String stopContainer;



}
