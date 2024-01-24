package cc.cloudcoding.auth.controller;


import cc.cloudcoding.auth.interfaces.AuthenticService;
import cc.cloudcoding.auth.model.req.LoginReq;
import cc.cloudcoding.auth.model.res.TokenRes;
import cc.cloudcoding.auth.model.security.OnlineUserInfo;
import cc.cloudcoding.auth.utils.OauthUtils;
import cc.cloudcoding.auth.utils.RsaUtilClient;
import cc.cloudcoding.base.model.CloudCodingConstant;
import cc.cloudcoding.base.model.RestResponse;
import cc.cloudcoding.base.model.ResultCode;
import cc.cloudcoding.id.domain.service.IDGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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

    @Qualifier("ulid")
    @Autowired
    private IDGenerator idGenerator;


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

    @GetMapping("/license")
    @Operation(summary = "获得加密公钥")
    public RestResponse license() throws Exception {


//        Map<Integer, String> integerStringMap = RSAUtil.genKeyPair();

        ConcurrentHashMap<String, Key> stringKeyConcurrentHashMap = RsaUtilClient.initKey();


        String s = idGenerator.nextID();
        stringRedisTemplate.opsForValue().set(CloudCodingConstant.PUBLIC_KEY_PREFIX + s, RsaUtilClient.getPublicKey(stringKeyConcurrentHashMap), 1, TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(CloudCodingConstant.PRIVATE_KEY_PREFIX + s, RsaUtilClient.getPrivateKey(stringKeyConcurrentHashMap), 1, TimeUnit.DAYS);
        HashMap<String, String> r = new HashMap<>();
        r.put("appid", s);
        r.put("pubKey", RsaUtilClient.getPublicKey(stringKeyConcurrentHashMap));
        return new RestResponse(200, "获得许可成功", r);

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
