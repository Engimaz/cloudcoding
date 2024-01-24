package cc.cloudcoding.gateway.filter;

import cc.cloudcoding.base.model.CloudCodingConstant;
import cn.hutool.jwt.JWTUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 往请求头中添加一个参数 使得下游微服务识别出来这一个来自网关的请求
 * @author: Administrator
 * @time: 2023/8/12 21:08
 */
@Component
public class GlobalGatewayParamFilter implements GlobalFilter, Ordered {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Map<String, Object> map = new ConcurrentHashMap<>(1);
        map.put(CloudCodingConstant.GATEWAY_CLAIM, CloudCodingConstant.GATEWAY_CLAIM_DATA);
        // 生成JWT
        String token = JWTUtil.createToken(map, CloudCodingConstant.SIGN_KEY.getBytes());
        ServerHttpRequest req = exchange.getRequest().mutate()
                .header(CloudCodingConstant.X_FROM_GATEWAY, token).build();
        return chain.filter(exchange.mutate().request(req).build());
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
