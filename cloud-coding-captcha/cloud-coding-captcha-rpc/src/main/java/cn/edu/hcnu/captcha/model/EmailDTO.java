package cn.edu.hcnu.captcha.model;

import cn.edu.hcnu.captcha.annotation.CodeTypeEnumValid;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/6/5 23:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "邮箱验证码数据传输类")
public class EmailDTO implements Serializable {
    @NotNull(message = "不存在邮箱字段")
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "对不起 这不是个合法邮箱")
    @Schema(description = "接收邮箱",defaultValue = "2291649168@qq.com")
    private String receiver;
    @NotNull(message = "不存在用途字段")
    @NotBlank(message = "用途不能为空")
    @CodeTypeEnumValid(message = "对不起 没有这个类型", enumClass = CodeType.class)
    @Schema(description = "验证码类型",allowableValues = {"sign-up-code","reset-code"},defaultValue = "sign-up-code")
    private String type;
    private String code;
}
