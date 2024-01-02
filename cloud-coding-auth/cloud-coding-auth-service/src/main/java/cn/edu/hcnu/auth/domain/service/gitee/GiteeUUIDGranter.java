package cn.edu.hcnu.auth.domain.service.gitee;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 自定义的授权模式，模式名称为：phone
 */
public class GiteeUUIDGranter extends AbstractTokenGranter {
    private static final String GRANT_TYPE = "gitee";

    private final AuthenticationManager authenticationManager;

    public GiteeUUIDGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices
            , ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
        String uuid = parameters.get("username");
        //将其中的密码移除
        parameters.remove("password");
        Authentication userAuth = new GiteeUUIDAuthenticationToken(uuid);
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
        //调用AuthenticationManager进行认证，内部会根据MobileAuthenticationToken找到对应的Provider进行认证
        userAuth = authenticationManager.authenticate(userAuth);
        if (userAuth == null || !userAuth.isAuthenticated()) {
            throw new InvalidGrantException("Could not authenticate gitee uuid: " + uuid);
        }
        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }
}