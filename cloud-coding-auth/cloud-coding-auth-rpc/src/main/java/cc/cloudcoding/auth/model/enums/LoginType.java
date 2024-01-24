package cc.cloudcoding.auth.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LoginType {
    /**
     * 账户
     */
    ACCOUNT(1),
    /**
     * 电话
     */
    PHONE(2),
    /**
     * 电子邮件
     */
    EMAIL(3),
    /**
     * qq
     */
    QQ(4),
    /**
     * 微信
     */
    WECHAT(5),
    /**
     * github
     */
    GITHUB(6),
    /**
     * gitee
     */
    GITEE(7);


    private Integer value;


}
