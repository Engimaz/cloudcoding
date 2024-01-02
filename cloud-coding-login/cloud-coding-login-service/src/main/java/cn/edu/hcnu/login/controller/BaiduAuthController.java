package cn.edu.hcnu.login.controller;

/**
 * @ClassName BaiduAuthController
 * @Description TODO
 * @Author liang
 * @Date 2023/6/25 22:19
 * @Version 1.0
 **/

import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.request.AuthBaiduRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 百度认证控制器 这个有bug 只能将 baidu 放置在最后一个
 *
 * @author liang
 * @date 2023/06/25
 */
@RestController
@RequestMapping("/oauth")
public class BaiduAuthController {

    @RequestMapping("/render/baidu")
    public void renderAuth(HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getAuthRequest();
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }

    @RequestMapping("/callback/baidu")
    public Object login(AuthCallback callback) {
        AuthRequest authRequest = getAuthRequest();
        return authRequest.login(callback);
    }

    private AuthRequest getAuthRequest() {
        return new AuthBaiduRequest(AuthConfig.builder()
                .clientId("qnQtnHvLS6p9WPOz1Ru9l7DY")
                .clientSecret("4M94dn8txZbTqBPeef08k0wWYkP5Kq5d")
                .redirectUri("http://127.0.0.1:3005/oauth/callback/baidu")
                .build());
    }
}
