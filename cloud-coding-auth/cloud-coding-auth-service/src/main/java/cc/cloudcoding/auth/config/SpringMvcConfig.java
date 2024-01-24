package cc.cloudcoding.auth.config;

import cc.cloudcoding.auth.interceptor.FormGatewayInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/12 21:58
 */
@Configuration
public class SpringMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new FormGatewayInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
