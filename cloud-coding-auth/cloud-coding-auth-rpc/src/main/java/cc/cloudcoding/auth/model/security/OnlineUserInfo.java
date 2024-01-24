package cc.cloudcoding.auth.model.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 保存登录用户的信息，此处可以根据业务需要扩展
 *
 * @author Administrator
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OnlineUserInfo extends JwtInformation {

    private String userId;

    private String username;

    private String[] authorities;
}
