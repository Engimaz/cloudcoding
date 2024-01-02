package cn.edu.hcnu.auth.exception;

import cn.edu.hcnu.auth.utils.ResponseUtils;
import cn.edu.hcnu.base.model.RestResponse;
import cn.edu.hcnu.base.model.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author AICHEN
 * 用于处理客户端想认证出错，包括客户端id、密码错误
 */
@Component
@Slf4j
public class OAuthServerAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * 认证失败处理器会调用这个方法返回提示信息
     * TODO 实际开发中可以自己定义，此处直接返回JSON数据：客户端认证失败错误提示
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        ResponseUtils.result(response, RestResponse.fail(ResultCode.CLIENT_AUTHENTICATION_FAILED), MediaType.APPLICATION_JSON_VALUE);
    }
}