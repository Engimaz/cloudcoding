package cn.edu.hcnu.auth.domain.service.authorization;

import cn.edu.hcnu.auth.domain.service.authorization.factory.AuthorizationFactory;
import cn.edu.hcnu.auth.infrastructure.repository.AuthorizationRepository;
import cn.edu.hcnu.auth.model.po.AuthorizationPO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorizationDomainService {

    @Autowired
    private AuthorizationFactory authorizationFactory;

    @Autowired
    private AuthorizationRepository authorizationRepository;

    /**
     * 添加一种认证方式
     * @param authorization
     * @return
     */
    public Authorization addAuthorization(Authorization authorization) {
        AuthorizationPO authorizationPO = authorizationFactory.createAuthorizationPO(authorization);
        boolean save = authorizationRepository.save(authorizationPO);
        if (save) {
            return authorizationFactory.createAuthorization(authorizationPO);
        }
        return null;
    }

    public boolean updatePassword(Authorization authorization) {
        return authorizationRepository.update(authorizationFactory.createAuthorizationPO(authorization),
                new LambdaQueryWrapper<AuthorizationPO>().eq(AuthorizationPO::getUserId, authorization.getUserId()));

    }

    public List<Authorization> getByUserId(Long userId) {
        List<AuthorizationPO> list = authorizationRepository.list(new LambdaQueryWrapper<AuthorizationPO>().eq(AuthorizationPO::getUserId, userId));
        return authorizationFactory.createAuthorization(list);
    }


    public Authorization getAuthorizationByEmail(String email) {
        AuthorizationPO one = authorizationRepository.getOne(new LambdaQueryWrapper<AuthorizationPO>().eq(AuthorizationPO::getIdentifier, email));
        return authorizationFactory.createAuthorization(one);
    }
}
