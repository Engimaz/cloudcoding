package cn.edu.hcnu.auth.domain.service.gitee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.stereotype.Component;

/**
 * 电话密码安全配置
 *
 * @author 不才小马  邮箱:Aichen517@qq.com
 * @date 2023/08/05
 */
@Component
public class GiteeUUIDSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {


    /**
     * 注入自定义的UserDetailsService 实现用户登录认证
     */
    @Autowired
    private GiteeUUIDUserDetailService giteeUUIDUserDetailService;

    /**
     * 注入自定义的UserDetailsService 实现用户登录认证
     */
    @Override
    public void configure(HttpSecurity builder) {
        //注入SmsCodeAuthenticationProvider
        GiteeUUIDAuthenticationProvider giteeUUIDAuthenticationProvider = new GiteeUUIDAuthenticationProvider(giteeUUIDUserDetailService);
        builder.authenticationProvider(giteeUUIDAuthenticationProvider);
    }
}