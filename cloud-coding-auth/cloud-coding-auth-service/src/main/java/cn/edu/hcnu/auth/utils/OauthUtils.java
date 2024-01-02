package cn.edu.hcnu.auth.utils;

import cn.edu.hcnu.auth.model.security.OnlineUserInfo;
import cn.edu.hcnu.base.model.CloudCodingConstant;

/**
 * @author AICHEN
 *         OAuth2.0工具类，从请求的线程中获取个人信息
 */
public class OauthUtils {

    /**
     * 获取当前请求登录用户的详细信息
     */
    public static OnlineUserInfo getCurrentUser() {
        return (OnlineUserInfo) RequestContextUtils.getRequest().getAttribute(CloudCodingConstant.ONLINE_USER_INFO);
    }
}
