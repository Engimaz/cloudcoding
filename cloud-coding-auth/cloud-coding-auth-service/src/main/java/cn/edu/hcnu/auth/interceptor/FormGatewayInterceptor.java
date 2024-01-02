package cn.edu.hcnu.auth.interceptor;

import cn.edu.hcnu.auth.utils.ResponseUtils;
import cn.edu.hcnu.base.model.CloudCodingConstant;
import cn.edu.hcnu.base.model.RestResponse;
import cn.edu.hcnu.base.model.ResultCode;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson2.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

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
