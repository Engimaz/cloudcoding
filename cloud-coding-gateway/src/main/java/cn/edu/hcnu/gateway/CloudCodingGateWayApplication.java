/*
 * @Description: 
 * @Version: 2.0
 * @Author: AICHEN
 * @Date: 2023-06-23 04:02:36
 * @LastEditors: AICHEN
 * @LastEditTime: 2023-10-14 00:25:38
 */
package cn.edu.hcnu.gateway;

import cn.edu.hcnu.gateway.model.SysParameterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@EnableConfigurationProperties(value = { SysParameterConfig.class })
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class CloudCodingGateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudCodingGateWayApplication.class);
    }
}
