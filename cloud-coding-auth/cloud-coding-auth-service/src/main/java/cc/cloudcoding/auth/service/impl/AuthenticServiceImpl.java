package cc.cloudcoding.auth.service.impl;

import cc.cloudcoding.auth.interfaces.AuthenticService;
import cc.cloudcoding.auth.model.res.TokenRes;
import cc.cloudcoding.base.model.CloudCodingConstant;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/11 22:45
 */
@DubboService
@Service
public class AuthenticServiceImpl implements AuthenticService {

    @Autowired
    private TokenEndpoint tokenEndpoint;

    @Override
    public TokenRes getAccessToken(String clientId, String clientSecret, String username, String password, String grantType, String scope) throws HttpRequestMethodNotSupportedException {
        User client = new User(clientId, clientSecret, new ArrayList<>());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(client, clientSecret, client.getAuthorities());
        Map<String, String> parameters = new ConcurrentHashMap<>(4);
        parameters.put("username", username);
        parameters.put("password", password);
        parameters.put("grant_type", grantType);
        OAuth2AccessToken body = tokenEndpoint.postAccessToken(token, parameters).getBody();

        return TokenRes.builder().accessToken(body.getValue()).refreshToken(body.getRefreshToken().getValue()).scope(body.getScope().toString()).jti(body.getAdditionalInformation().get(CloudCodingConstant.JTI).toString()).expiresIn(body.getExpiresIn()).userId(body.getAdditionalInformation().get(CloudCodingConstant.USER_ID).toString()).tokenType(body.getTokenType()).build();


    }

}
