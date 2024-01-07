package cn.edu.hcnu.base.model;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/12 21:24
 */
public abstract class CloudCodingConstant {
    public final static String SIGN_KEY = "cloud-coding";

    public final static String TOKEN_NAME = "jwt-token";

    public final static String PRINCIPAL_NAME = "principal";

    public static final String AUTHORITIES_NAME = "authorities";

    public static final String USER_ID = "user_id";

    public static final String JTI = "jti";

    public static final String EXPR = "expr";

    /**
     * JWT令牌黑名单的KEY
     */
    public final static String JTI_KEY_PREFIX = "oauth2:black:";

    /**
     * 角色前缀
     */
    public final static String ROLE_PREFIX = "ROLE_";

    public final static String METHOD_SUFFIX = ":";

    public final static String ROLE_ROOT_CODE = "ROLE_ROOT";

    public final static String ONLINE_USER_INFO = "X-User-Info";

    public static final String X_FROM_GATEWAY = "X-Gateway-Request";
    public static final String GATEWAY_CLAIM = "gateway-claim";
    public static final String GATEWAY_CLAIM_DATA = "form-gateway-data";
    public static final String PUBLIC_KEY_PREFIX = "public:key:";
    public static final String PRIVATE_KEY_PREFIX = "private:key:";

}
