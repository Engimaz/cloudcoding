package cn.edu.hcnu.auth.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/12 16:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginReq {

    private String username;

    private String password;

    private String grant_type;

    private String client_id;

    private String client_secret;

    private String scope;
}
