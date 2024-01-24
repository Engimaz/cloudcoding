package cc.cloudcoding.gateway.exception;


import cc.cloudcoding.base.model.RestResponse;
import cn.hutool.json.JSONUtil;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

import static cc.cloudcoding.base.model.ResultCode.NO_PERMISSION;

/**
 * @author AICHEN
 * 自定义返回结果：没有权限访问时
 */
@Component
public class RequestAccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FORBIDDEN);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        String body = JSONUtil.toJsonStr(RestResponse.fail(NO_PERMISSION));
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}