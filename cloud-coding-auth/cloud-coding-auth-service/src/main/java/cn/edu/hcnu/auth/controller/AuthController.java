package cn.edu.hcnu.auth.controller;


import cn.edu.hcnu.auth.interfaces.AuthenticService;
import cn.edu.hcnu.auth.model.req.LoginReq;
import cn.edu.hcnu.auth.model.res.TokenRes;
import cn.edu.hcnu.auth.model.security.OnlineUserInfo;
import cn.edu.hcnu.auth.utils.OauthUtils;
import cn.edu.hcnu.base.model.CloudCodingConstant;
import cn.edu.hcnu.base.model.RestResponse;
import cn.edu.hcnu.base.model.ResultCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/oauth")
@Slf4j
@Tag(name = "授权服务")
public class AuthController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private AuthenticService authenticService;
    @Autowired
    private CheckTokenEndpoint checkTokenEndpoint;


    @PostMapping("/logout")
    @Operation(summary = "用户下线")
    public RestResponse logout() {
        OnlineUserInfo loginVal = OauthUtils.getCurrentUser();
        if (loginVal != null) {
            log.info("令牌唯一ID：{},过期时间：{}", loginVal.getJti(), loginVal.getExpireIn());
            //这个jti放入redis中，并且过期时间设置为token的过期时间
            stringRedisTemplate.opsForValue().set(CloudCodingConstant.JTI_KEY_PREFIX + loginVal.getJti(), "", loginVal.getExpireIn(), TimeUnit.SECONDS);
            return new RestResponse(200, "注销成功");
        }
        return RestResponse.fail(ResultCode.LOGOUT_FAIL);
    }


    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public RestResponse<TokenRes> login(@RequestBody LoginReq loginReq) throws HttpRequestMethodNotSupportedException {
        TokenRes token = authenticService.getAccessToken(loginReq.getClient_id(), loginReq.getClient_secret(), loginReq.getUsername(), loginReq.getPassword(), loginReq.getGrant_type(), loginReq.getScope());
        return RestResponse.success(ResultCode.SUCCESS, token);
    }

    @PostMapping(value = "/check_token")
    public RestResponse<Map<String, ?>> checkToken(@RequestParam("token") String value) {
        Map<String, ?> map = checkTokenEndpoint.checkToken(value);
        return RestResponse.success(ResultCode.SUCCESS, map);
    }


}
