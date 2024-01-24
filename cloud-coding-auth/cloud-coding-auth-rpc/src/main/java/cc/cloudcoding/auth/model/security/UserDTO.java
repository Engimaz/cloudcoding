package cc.cloudcoding.auth.model.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String nickname;
    private String email;
    private String phone;
    private String avatar;
    private String sex;
    private String status;
    private LocalDateTime crateTime;
    private LocalDateTime updateTime;
}
