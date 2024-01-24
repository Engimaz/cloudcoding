package cc.cloudcoding.manager.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author aichen
 * @since 2023-08-05 02:13:57
 */
@Getter
@Setter
@TableName("authorization")
public class AuthorizationPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    /**
     * 登录类型(手机号/邮箱) 或第三方应用名称 (微信/微博等)
     */
    private Integer identityType;

    /**
     * 手机号/邮箱/第三方的唯一标识
     */
    private String identifier;

    /**
     * 密码凭证 (自建账号的保存密码, 第三方的保存 token)
     */
    private String credential;
}
