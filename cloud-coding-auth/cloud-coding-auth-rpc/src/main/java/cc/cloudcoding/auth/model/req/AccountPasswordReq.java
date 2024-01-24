package cc.cloudcoding.auth.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/10 23:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountPasswordReq {
    private String account;
    private String password;
}
