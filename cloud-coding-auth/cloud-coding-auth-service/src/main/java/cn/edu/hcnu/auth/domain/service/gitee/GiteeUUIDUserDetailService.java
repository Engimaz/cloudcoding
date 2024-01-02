package cn.edu.hcnu.auth.domain.service.gitee;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 短信密码用户细节服务
 * 自定义的UserDetailService，从数据库中
 *
 * @author liang
 * @date 2023/06/25
 */
public interface GiteeUUIDUserDetailService {


    /**
     * 加载用户uuid
     *
     * @param uuid uuid
     * @return {@link UserDetails}
     * @throws UsernameNotFoundException 用户名没有发现异常
     */
    UserDetails loadUserByUuid(String uuid) throws UsernameNotFoundException;
}
