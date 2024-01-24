package cc.cloudcoding.auth.model.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRes implements Serializable {
    private String id;
    private String nickname;
    private String avatar;
    private String sex;
    private String status;
    private String email;
}
