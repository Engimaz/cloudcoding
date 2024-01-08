package cn.edu.hcnu.auth.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Lazy
public class UserReq {
    private String id;
    @Email
    private String email;
    private String phone;
    // 限制长度为4
    @Length(min = 4, max = 4)
    private String code;

    private String nickname;

    @Value("0")
    private String status;
    private String password;

    private String avatar;

    private Integer sex;

}
