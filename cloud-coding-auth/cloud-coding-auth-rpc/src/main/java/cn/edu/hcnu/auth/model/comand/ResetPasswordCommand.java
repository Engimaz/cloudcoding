package cn.edu.hcnu.auth.model.comand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.stream.Stream;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/22 1:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordCommand {
    private String code;
    private String phone;
    private String email;
    private String password;
}
