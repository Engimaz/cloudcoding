package cn.edu.hcnu.auth.domain.service.password;

import cn.edu.hcnu.auth.domain.service.password.service.PhonePasswordUserDetailService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author AICHEN
 * 自定义AuthenticationProvider，处理手机号。密码登录
 * 主要的逻辑：根据userDetailService从数据库中查询用户，对比密码
 */
public class PhonePasswordAuthenticationProvider implements AuthenticationProvider {

    private final PhonePasswordUserDetailService userDetailService;

    private final PasswordEncoder passwordEncoder;

    public PhonePasswordAuthenticationProvider(PhonePasswordUserDetailService userDetailService,
                                               PasswordEncoder passwordEncoder) {
        this.userDetailService = userDetailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        PhonePasswordAuthenticationToken authenticationToken = (PhonePasswordAuthenticationToken) authentication;
        String mobile = (String) authenticationToken.getPrincipal();
        String password = (String) authenticationToken.getCredentials();
        // 查询数据库，加载用户详细信息
        UserDetails user = userDetailService.loadUserByMobile(mobile);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("手机号或密码错误");
        }
        PhonePasswordAuthenticationToken authenticationResult = new PhonePasswordAuthenticationToken(user, password, user.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    /**
     * Spring Security会根据这个方法判断当前Provider是否支持处理
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return PhonePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}