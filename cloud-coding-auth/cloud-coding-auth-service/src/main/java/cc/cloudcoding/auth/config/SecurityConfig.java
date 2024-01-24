package cc.cloudcoding.auth.config;

import cc.cloudcoding.auth.domain.service.password.PhonePasswordSecurityConfig;
import cc.cloudcoding.auth.domain.service.gitee.GiteeUUIDSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author AICHEN
 * spring security的安全配置
 */
@Configuration
@ComponentScan(basePackages = {"cc.cloudcoding.*"})
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 注入自定义的UserDetailsService 实现用户登录认证
     */
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PhonePasswordSecurityConfig smsCodeSecurityConfig;

    @Autowired
    private GiteeUUIDSecurityConfig giteeUUIDSecurityConfig;

    /**
     * 加密算法 用户加密密码
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    public static void main(String[] args) {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123");
        System.out.println("encode = " + encode);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // todo 允许表单登录
        http
                // 注入自定义的授权配置类
                .apply(smsCodeSecurityConfig)
                .and()
                .apply(giteeUUIDSecurityConfig)
                .and()
                .authorizeRequests()
                // 注销的接口需要放行
                .antMatchers("/oauth/logout", "/oauth/license", "/**/logout", "/**/user/new", "/**/user/info/*", "/**/user/update", "/**/user/list", "/**/login", "**/check_token", "**/v3/api-docs/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf()
                .disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 从数据库中查询用户信息
        auth.userDetailsService(userDetailsService);
    }

    /**
     * AuthenticationManager对象在OAuth2认证服务中要使用，提前放入IOC容器中
     * Oauth的密码模式需要
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}