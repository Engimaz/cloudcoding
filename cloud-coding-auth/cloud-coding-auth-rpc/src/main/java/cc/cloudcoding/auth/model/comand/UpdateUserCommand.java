package cc.cloudcoding.auth.model.comand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserCommand {
    private String id;
    private String nickname;
    private String avatar;
    private Integer sex;
    private String password;
    private Long status;
    private String phone;
    private String email;
    private String code;
    private String idnumber;
}
