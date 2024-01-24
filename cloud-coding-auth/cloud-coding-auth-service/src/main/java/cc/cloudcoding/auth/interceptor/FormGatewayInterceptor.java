package cc.cloudcoding.auth.interceptor;

import cc.cloudcoding.auth.utils.ResponseUtils;
import cc.cloudcoding.base.model.CloudCodingConstant;
import cc.cloudcoding.base.model.RestResponse;
import cc.cloudcoding.base.model.ResultCode;
import cn.hutool.jwt.JWTUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/12 21:34
 */
public class FormGatewayInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(CloudCodingConstant.X_FROM_GATEWAY);
        if (!StringUtils.isNotBlank(token)
                ||
                !JWTUtil.verify(token, CloudCodingConstant.SIGN_KEY.getBytes())
                ||
                !CloudCodingConstant.GATEWAY_CLAIM_DATA.equals(JWTUtil.parseToken(token).getPayload().getClaim(CloudCodingConstant.GATEWAY_CLAIM).toString())) {


            ResponseUtils.result(response, RestResponse.fail (ResultCode.GATEWAY_ERROR), MediaType.APPLICATION_JSON_VALUE);
            return false;
        }
        return true;
    }


}
