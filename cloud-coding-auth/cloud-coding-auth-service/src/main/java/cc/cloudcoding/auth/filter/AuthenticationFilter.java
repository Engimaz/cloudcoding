package cc.cloudcoding.auth.filter;

import cc.cloudcoding.auth.model.security.OnlineUserInfo;
import cc.cloudcoding.base.model.CloudCodingConstant;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author AICHEN
 * 微服务过滤器，解密网关传递的用户信息，将其放入request中，便于后期业务方法直接获取用户信息
 */
@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    /**
     * 具体方法主要分为两步
     * 1. 解密网关传递的信息
     * 2. 将解密之后的信息封装放入到request中
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 获取请求头中的加密的用户信息
        String token = request.getHeader(CloudCodingConstant.TOKEN_NAME);
        if (StrUtil.isNotBlank(token)) {
            // 解密
            String json = Base64.decodeStr(token);
            JSONObject jsonObject = JSON.parseObject(json);
            // 获取用户身份信息、权限信息
            // 放入LoginVal
            OnlineUserInfo loginVal = new OnlineUserInfo();

            String principal = jsonObject.getString(CloudCodingConstant.PRINCIPAL_NAME);
            String userId = jsonObject.getString(CloudCodingConstant.USER_ID);
            String jti = jsonObject.getString(CloudCodingConstant.JTI);
            Long expireIn = jsonObject.getLong(CloudCodingConstant.EXPR);
            JSONArray tempJsonArray = jsonObject.getJSONArray(CloudCodingConstant.AUTHORITIES_NAME);

            if (tempJsonArray != null) {
                // 权限
                String[] authorities = tempJsonArray.toArray(new String[0]);
                loginVal.setAuthorities(authorities);
            }


            loginVal.setUserId(userId);
            loginVal.setUsername(principal);

            loginVal.setJti(jti);
            loginVal.setExpireIn(expireIn);
            // 放入request的attribute中
            request.setAttribute(CloudCodingConstant.ONLINE_USER_INFO, loginVal);
        }
        filterChain.doFilter(request, response);
    }
}