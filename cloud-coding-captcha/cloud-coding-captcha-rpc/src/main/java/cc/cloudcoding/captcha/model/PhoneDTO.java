package cc.cloudcoding.captcha.model;

import cc.cloudcoding.captcha.annotation.CodeTypeEnumValid;
import cc.cloudcoding.captcha.annotation.PhoneNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/6/6 10:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "手机验证码传输实体类")
public class PhoneDTO implements Serializable {
    @NotNull(message = "没有电话号码")
    @NotBlank(message = "电话号码不能为空")
    @PhoneNumber(message = "对不起 这不是个合法号码")
    @Schema(description = "接收号码",defaultValue = "15177457696")
    private String receiver;

    private String code;

    @NotNull(message = "不存在用途字段")
    @NotBlank(message = "用途不能为空")
    @CodeTypeEnumValid(message = "对不起 没有这个类型", enumClass = CodeType.class)
    @Schema(description = "验证码类型",allowableValues = {"sign-up-code","reset-code"},defaultValue = "sign-up-code")
    private String type;
}
