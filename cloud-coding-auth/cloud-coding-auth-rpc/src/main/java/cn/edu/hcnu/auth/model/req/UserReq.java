package cn.edu.hcnu.auth.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
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
    private String repassword;
    private String nickname;
    private String password;

    private String avatar;

    private Integer sex;
//    @AssertTrue(message = "两次输入的密码不一致")
//    public boolean isPasswordMatch() {
//        // 在这里编写验证逻辑，检查密码和确认密码是否一致
//        return password != null && password.equals(repassword);
//    }
}
