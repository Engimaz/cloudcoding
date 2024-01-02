package cn.edu.hcnu.auth.domain.service.password;

import cn.edu.hcnu.auth.domain.service.password.service.PhonePasswordUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.stereotype.Component;

/**
 * 电话密码安全配置
 *
 * @author 不才小马  邮箱:Aichen517@qq.com
 * @date 2023/08/05
 */
@Component
public class PhonePasswordSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    /**
     * 密码编码器
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 用户细节服务
     */
    @Autowired
    private PhonePasswordUserDetailService userDetailService;

    /**
     * 短信验证码配置器
     *  所有的配置都可以移步到WebSecurityConfig
     *  builder.authenticationProvider() 相当于 auth.authenticationProvider();
     *  使用外部配置必须要在WebSecurityConfig中用http.apply(smsCodeSecurityConfig)将配置注入进去
     */
    @Override
    public void configure(HttpSecurity builder) {
        //注入SmsCodeAuthenticationProvider
        PhonePasswordAuthenticationProvider smsCodeAuthenticationProvider = new PhonePasswordAuthenticationProvider(userDetailService,passwordEncoder);
        builder.authenticationProvider(smsCodeAuthenticationProvider);
    }
}