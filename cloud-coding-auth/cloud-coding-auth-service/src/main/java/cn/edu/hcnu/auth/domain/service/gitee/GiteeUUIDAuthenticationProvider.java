package cn.edu.hcnu.auth.domain.service.gitee;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author AICHEN
 * 自定义AuthenticationProvider，处理手机号。密码登录
 * 主要的逻辑：根据userDetailService从数据库中查询用户，对比密码
 */
public class GiteeUUIDAuthenticationProvider implements AuthenticationProvider {

    private final GiteeUUIDUserDetailService giteeUUIDUserDetailService;


    public GiteeUUIDAuthenticationProvider(GiteeUUIDUserDetailService giteeUUIDUserDetailService) {
        this.giteeUUIDUserDetailService = giteeUUIDUserDetailService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        GiteeUUIDAuthenticationToken authenticationToken = (GiteeUUIDAuthenticationToken) authentication;
        String uuid = (String) authenticationToken.getPrincipal();
        // 查询数据库，加载用户详细信息
        UserDetails user = giteeUUIDUserDetailService.loadUserByUuid(uuid);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        GiteeUUIDAuthenticationToken authenticationResult = new GiteeUUIDAuthenticationToken(user, "N/A", user.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    /**
     * Spring Security会根据这个方法判断当前Provider是否支持处理
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return GiteeUUIDAuthenticationToken.class.isAssignableFrom(authentication);
    }
}