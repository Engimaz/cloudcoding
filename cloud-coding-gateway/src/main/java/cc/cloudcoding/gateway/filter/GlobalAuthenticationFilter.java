package cc.cloudcoding.gateway.filter;


import cc.cloudcoding.base.model.CloudCodingConstant;
import cc.cloudcoding.gateway.model.SysParameterConfig;
import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * @author AICHEN
 * 全局过滤器，对token的拦截，解析token放入header中，便于下游微服务获取用户信息
 * 分为如下几步：
 * 1、白名单直接放行
 * 2、校验token
 * 3、读取token中存放的用户信息
 * 4、重新封装用户信息，加密成功json数据放入请求头中传递给下游微服务
 */
@Component
@Slf4j
@AllArgsConstructor
public class GlobalAuthenticationFilter implements GlobalFilter, Ordered {
    /**
     * JWT令牌的服务
     */
    private TokenStore tokenStore;

    private StringRedisTemplate stringRedisTemplate;

    /**
     * 系统参数配置
     */
    private SysParameterConfig sysConfig;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestUrl = exchange.getRequest().getPath().value();

        // 获得路由
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);


        log.info("转发后的完整地址 address: {}", route.getUri().toString() + exchange.getRequest().getPath().toString());


        log.info("本次请求的url:{}", requestUrl);

        // 1、 检查token是否存在
        String token = getToken(exchange);
        if (StringUtils.isBlank(token)) {
            log.error("token 不存在");

            // 1、token 不在的情况下 是不是在白名单内
            if (!this.checkUrls(sysConfig.getIgnoreUrls(), requestUrl)) {
                throw new InvalidTokenException("token 不存在");
            } else {
                return chain.filter(exchange);
            }
        } else {

            // 3 判断是否是有效的token
            OAuth2AccessToken oAuth2AccessToken;

            oAuth2AccessToken = tokenStore.readAccessToken(token);
            Map<String, Object> additionalInformation = oAuth2AccessToken.getAdditionalInformation();
            // 令牌的唯一ID
            String jti = additionalInformation.get(CloudCodingConstant.JTI).toString();
            // 查看黑名单中是否存在这个jti，如果存在则这个令牌不能用
            Boolean hasKey = stringRedisTemplate.hasKey(CloudCodingConstant.JTI_KEY_PREFIX + jti);
            if (Boolean.TRUE.equals(hasKey)) {
                throw new InvalidTokenException("token 无效");
            }
            // 取出用户身份信息
            String userName = additionalInformation.get("user_name").toString();

            // 获取用户权限
            List<String> authorities = (List<String>) additionalInformation.get("authorities");
            // 从additionalInformation取出userId
            String userId = additionalInformation.get(CloudCodingConstant.USER_ID).toString();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(CloudCodingConstant.PRINCIPAL_NAME, userName);
            jsonObject.put(CloudCodingConstant.AUTHORITIES_NAME, authorities);
            // 过期时间，单位秒
            jsonObject.put(CloudCodingConstant.EXPR, oAuth2AccessToken.getExpiresIn());
            jsonObject.put(CloudCodingConstant.JTI, jti);
            // 封装到JSON数据中
            jsonObject.put(CloudCodingConstant.USER_ID, userId);
            // 将解析后的token加密放入请求头中，方便下游微服务解析获取用户信息
            String base64 = Base64.encode(jsonObject.toJSONString());
            // 放入请求头中
            ServerHttpRequest tokenRequest = exchange.getRequest().mutate().header(CloudCodingConstant.TOKEN_NAME, base64)
                    .build();
            ServerWebExchange build = exchange.mutate().request(tokenRequest).build();
            return chain.filter(build);
        }

    }


    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 对url进行校验匹配
     */
    private boolean checkUrls(List<String> urls, String path) {

        AntPathMatcher pathMatcher = new AntPathMatcher();
        log.info("{}", urls);
        for (String url : urls) {
            if (pathMatcher.match(url, path)) {
                log.info("url：{} 在白名单中 放行本次请求", url);
                return true;
            }

        }
        return false;
    }

    /**
     * 从请求头中获取Token
     */
    private String getToken(ServerWebExchange exchange) {
        String tokenStr = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (StringUtils.isBlank(tokenStr)) {
            return null;
        }
        String token = tokenStr.split(" ")[1];
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return token;
    }


}
