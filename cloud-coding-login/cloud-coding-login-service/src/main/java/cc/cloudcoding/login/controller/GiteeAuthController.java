package cc.cloudcoding.login.controller;

/**
 * @ClassName RestAuthController
 * @Description TODO
 * @Author liang
 * @Date 2023/6/25 21:59
 * @Version 1.0
 **/

import cc.cloudcoding.auth.interfaces.AuthenticService;
import cc.cloudcoding.auth.model.res.TokenRes;
import cc.cloudcoding.base.model.RestResponse;
import cc.cloudcoding.base.model.ResultCode;
import com.alibaba.fastjson2.JSONObject;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthGiteeRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/oauth/gitee")
public class GiteeAuthController {

    @DubboReference
    private AuthenticService authenticService;

    @RequestMapping("/render")
    public void renderAuth(HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getAuthRequest();
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }

    @RequestMapping("/callback")
    public RestResponse<TokenRes> login(AuthCallback callback) throws HttpRequestMethodNotSupportedException {
        AuthRequest authRequest = getAuthRequest();
        AuthResponse login = authRequest.login(callback);
        JSONObject remoteObject = JSONObject.parse(JSONObject.toJSONString(login));
        Object giteeUser = remoteObject.get("data");
        JSONObject user = JSONObject.parse(JSONObject.toJSONString(giteeUser));
        // 远程查询用户
        String giteeId = user.get("uuid").toString();
        TokenRes accessToken = authenticService.getAccessToken("cloud-coding", "N/A", giteeId, "N/A", "gitee", "all");

        return RestResponse.success(ResultCode.SUCCESS, accessToken);
    }

    private AuthRequest getAuthRequest() {
        return new AuthGiteeRequest(AuthConfig.builder()
                .clientId("c96372cbf973f2a13f82052f3efb698acab1444c58a4d36af0b61f0f8aec2448")
                .clientSecret("7128585ee0c2152acb29e68e65cee79d4565ff305ba841ed951bac80ddf4cb12")
                .redirectUri("http://127.0.0.1:3005/oauth/gitee/callback")
                .build());
    }
}
