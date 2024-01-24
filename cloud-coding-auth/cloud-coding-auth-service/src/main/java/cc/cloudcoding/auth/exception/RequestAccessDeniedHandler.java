package cc.cloudcoding.auth.exception;

import cc.cloudcoding.auth.utils.ResponseUtils;
import cc.cloudcoding.base.model.RestResponse;
import cc.cloudcoding.base.model.ResultCode;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author AICHEN
 * <p>
 * 当认证后的用户访问受保护的资源时，权限不够，则会进入这个处理器
 */
@Component
public class RequestAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        ResponseUtils.result(response, RestResponse.fail (ResultCode.NO_PERMISSION), MediaType.APPLICATION_JSON_VALUE);
    }
}