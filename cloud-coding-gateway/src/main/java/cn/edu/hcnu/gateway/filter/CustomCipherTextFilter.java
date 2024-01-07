package cn.edu.hcnu.gateway.filter;

import cn.edu.hcnu.base.model.CloudCodingConstant;
import cn.edu.hcnu.gateway.util.RsaUtilClient;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 自定义密文过滤器
 *
 * @author feng
 */
@Slf4j
@Component
public class CustomCipherTextFilter implements GlobalFilter, Ordered {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 获取请求体
        ServerHttpRequest request = exchange.getRequest();
        // 获取响应体
        ServerHttpResponse response = exchange.getResponse();
        // 请求头
        HttpHeaders headers = request.getHeaders();
        // 请求方法
        HttpMethod method = request.getMethod();

        List<String> encryptId = headers.get("X-Encrypt-Id");
        List<String> fields = headers.get("X-Encrypt-Field");

        // 满足条件，进行过滤
        if (encryptId != null && encryptId.size() == 1 && fields != null && fields.size() == 1 && isNeedFilterMethod(method) && isNeedFilterContentType(headers.getContentType())) {

            return DataBufferUtils.join(request.getBody())
                    .flatMap(dataBuffer -> {
                        try {
                            // 获取请求参数
                            String originalRequestBody = getOriginalRequestBody(dataBuffer);

                            // 解密请求参数
                            String decryptRequestBody = decryptRequest(originalRequestBody, encryptId.get(0), fields.get(0));

                            // 装饰新的请求体
                            ServerHttpRequestDecorator requestDecorator = serverHttpRequestDecorator(request, decryptRequestBody);


                            // 使用新的请求和响应转发
                            ServerWebExchange serverWebExchange = exchange.mutate().request(requestDecorator).response(response).build();

                            // 放行拦截
                            return chain.filter(serverWebExchange);
                        } catch (Exception e) {
                            log.error("密文过滤器加解密错误", e);
                            return Mono.empty();
                        } finally {
                            DataBufferUtils.release(dataBuffer);
                        }
                    });
        }
        return chain.filter(exchange);
    }


    private String decryptRequest(String originalRequestBody, String encryptId, String fields) throws Exception {
        // 获得私钥
        String s = stringRedisTemplate.opsForValue().get(CloudCodingConstant.PRIVATE_KEY_PREFIX + encryptId);

        if (s != null && !s.isEmpty()) {
            String[] encryptFields = fields.split(",");
            Map<String, String> map = JSON.parseObject(originalRequestBody, Map.class);
            for (String encryptField : encryptFields) {
                String o = map.get(encryptField);
                if (o != null) {
                    String decrypt = new String(RsaUtilClient.decryptByPrivateKey(o, s));
                    map.put(encryptField, decrypt);
                }
            }
            return JSON.toJSONString(map);
        }
        return originalRequestBody;
    }


    /**
     * 获取原始的请求参数
     *
     * @param dataBuffer 数据缓冲
     * @return 原始的请求参数
     */
    private String getOriginalRequestBody(DataBuffer dataBuffer) {
        byte[] bytes = new byte[dataBuffer.readableByteCount()];
        dataBuffer.read(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }


    private boolean isNeedFilterMethod(HttpMethod method) {
        return NEED_FILTER_METHOD_SET.contains(method);
    }

    private boolean isNeedFilterContentType(MediaType mediaType) {
        return NEED_FILTER_MEDIA_TYPE_SET.contains(mediaType) || "json".equals(mediaType.getSubtype());
    }

    private ServerHttpRequestDecorator serverHttpRequestDecorator(ServerHttpRequest originalRequest, String decryptRequestBody) {
        return new ServerHttpRequestDecorator(originalRequest) {
            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(super.getHeaders());
                httpHeaders.remove(HttpHeaders.CONTENT_LENGTH);
                httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                return httpHeaders;
            }

            @Override
            public Flux<DataBuffer> getBody() {
                byte[] bytes = decryptRequestBody.getBytes(StandardCharsets.UTF_8);
                return Flux.just(new DefaultDataBufferFactory().wrap(bytes));
            }
        };
    }


    private static final Set<HttpMethod> NEED_FILTER_METHOD_SET = Set.of(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT);
    private static final Set<MediaType> NEED_FILTER_MEDIA_TYPE_SET = Set.of(MediaType.APPLICATION_JSON);

    @Override
    public int getOrder() {
        return -1;
    }


}

