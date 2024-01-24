package cc.cloudcoding.auth.domain.service.password.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 短信密码用户细节服务
 * 自定义的UserDetailService，从数据库中
 *
 * @author liang
 * @date 2023/06/25
 */
public interface PhonePasswordUserDetailService {

    /**
     * 根据手机号查询用户信息
     * @param mobile  手机号码
     */
    UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException;
}
