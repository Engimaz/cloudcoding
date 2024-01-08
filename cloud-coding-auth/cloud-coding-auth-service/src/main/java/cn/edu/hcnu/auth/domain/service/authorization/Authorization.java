package cn.edu.hcnu.auth.domain.service.authorization;

import cn.edu.hcnu.auth.infrastructure.repository.AuthorizationRepository;
import cn.edu.hcnu.auth.model.po.AuthorizationPO;
import cn.edu.hcnu.base.execption.CloudCodingException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Scope("prototype")
public class Authorization {

    private Long id;

    private Long userId;


    private Integer identityType;


    // 邮箱 账号
    private String identifier;

    // 密码
    private String credential;
    @Autowired
    private AuthorizationRepository authorizationRepository;

    public void save() {
        AuthorizationPO po = new AuthorizationPO();
        po.setId(this.id);
        po.setUserId(this.userId);
        po.setIdentityType(this.identityType);
        po.setIdentifier(this.identifier);
        po.setCredential(this.credential);
        boolean save = authorizationRepository.save(po);
        if (!save) {
            throw new CloudCodingException("注册失败");
        }
    }

    public void update() {
        AuthorizationPO po = new AuthorizationPO();
        po.setId(this.id);
        po.setUserId(this.userId);
        po.setIdentityType(this.identityType);
        po.setIdentifier(this.identifier);
        po.setCredential(this.credential);
        boolean save = authorizationRepository.updateById(po);
        if (!save) {
            throw new CloudCodingException("更新失败");
        }
    }

    public void renderByEmail() {
        AuthorizationPO one = authorizationRepository.getOne(new LambdaQueryWrapper<AuthorizationPO>().eq(AuthorizationPO::getIdentifier, this.identifier));
        this.setId(one.getId());
        this.setUserId(one.getUserId());
        this.setCredential(one.getCredential());
        this.setIdentifier(one.getIdentifier());
        this.setIdentityType(one.getIdentityType());
    }
}
