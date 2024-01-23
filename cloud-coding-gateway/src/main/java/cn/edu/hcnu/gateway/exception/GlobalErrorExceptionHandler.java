package cn.edu.hcnu.gateway.exception;

import cn.edu.hcnu.base.model.RestResponse;
import cn.edu.hcnu.base.model.ResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 用于网关的全局异常处理
 *
 * @Order(-1)：优先级一定要比ResponseStatusExceptionHandler低
 */
@Slf4j
@Order(-1)
@Component
@RequiredArgsConstructor
public class GlobalErrorExceptionHandler implements ErrorWebExceptionHandler {
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        // 检测当前响应是否已经提交了，如果已经提交了，则直接返回
        if (response.isCommitted()) {
            return Mono.empty(); // 改为返回已完成的Mono
        }

        // 默认是系统异常
        RestResponse<Object> restResponse = RestResponse.fail(ResultCode.ERROR);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 默认状态

        // 微服务没有启动或者连接超时
        if (ex instanceof NotFoundException) {
            restResponse = RestResponse.fail(ResultCode.NOT_FOUND);
            status = HttpStatus.SERVICE_UNAVAILABLE; // 改为NOT_FOUND
        }
        // 处理TOKEN失效的异常
        else if (ex instanceof InvalidTokenException) {
            restResponse = RestResponse.fail(ResultCode.INVALID_TOKEN);
            status = HttpStatus.UNAUTHORIZED;
        }

        // JSON格式返回
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        DataBufferFactory bufferFactory = response.bufferFactory();
        DataBuffer dataBuffer;

        try {
            dataBuffer = bufferFactory.wrap(new ObjectMapper().writeValueAsBytes(restResponse));
        } catch (Exception e) {
            log.error("Error writing response", e); // 修改为记录实际发生的异常e
            dataBuffer = bufferFactory.wrap(new byte[0]); // 确保有响应返回
        }

        // 现在设置状态码
        response.setStatusCode(status);

        return response.writeWith(Mono.just(dataBuffer));
    }

}
