package cn.edu.hcnu.auth.model.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/11 23:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenRes implements Serializable {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private String scope;
    private String jti;
    private Integer expiresIn;
    private String userId;
}
