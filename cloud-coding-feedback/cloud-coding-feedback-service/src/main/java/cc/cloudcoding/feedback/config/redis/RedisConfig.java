package cc.cloudcoding.feedback.config.redis;

import com.alibaba.fastjson2.support.spring.data.redis.GenericFastJsonRedisSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;

/**
 * redis配置
 *
 * @author Administrator
 * @date 2023/06/05
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfig {
    @Value("${spring.redis.host:127.0.0.1}")
    private String host;

    @Value("${spring.redis.port:6379}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.database:0}")
    private int database;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder().build();
        RedisStandaloneConfiguration serverConfig = new RedisStandaloneConfiguration(host, port);
        serverConfig.setPassword(password);
        return new LettuceConnectionFactory(serverConfig, clientConfig);
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getDeclaringClass().getName());
            Arrays.stream(params).map(Object::toString).forEach(sb::append);
            return sb.toString();
        };
    }

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        GenericFastJsonRedisSerializer fastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
        redisTemplate.setDefaultSerializer(fastJsonRedisSerializer);// 设置默认的Serialize，包含 keySerializer & valueSerializer

        return redisTemplate;
    }

}
