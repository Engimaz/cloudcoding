package cn.edu.hcnu.auth.interfaces;

import cn.edu.hcnu.auth.model.res.TokenRes;
import org.springframework.web.HttpRequestMethodNotSupportedException;

public interface AuthenticService {
    /**
     * 获取访问令牌
     *
     * @param clientId     客户机id
     * @param clientSecret 客户秘密
     * @param username     用户名
     * @param password     密码
     * @param grantType    授权类型
     * @param scope        范围
     * @return {@link TokenRes}
     * @throws HttpRequestMethodNotSupportedException http请求方法不支持异常
     */
    TokenRes getAccessToken(String clientId, String clientSecret, String username, String password, String grantType, String scope) throws HttpRequestMethodNotSupportedException;
}
