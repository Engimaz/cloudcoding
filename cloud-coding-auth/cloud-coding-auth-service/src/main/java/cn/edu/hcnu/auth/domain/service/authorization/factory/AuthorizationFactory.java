package cn.edu.hcnu.auth.domain.service.authorization.factory;

import cn.edu.hcnu.auth.domain.service.authorization.Authorization;
import cn.edu.hcnu.auth.model.po.AuthorizationPO;
import cn.edu.hcnu.id.domain.service.IDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorizationFactory {

    @Autowired
    @Qualifier("snowflake")
    private IDGenerator idGenerator;

    public AuthorizationPO createAuthorizationPO(Authorization authorization) {
        if (authorization == null) {
            return null;
        }
        AuthorizationPO authorizationPO = new AuthorizationPO();
        authorizationPO.setCredential(authorization.getCredential());
        if (authorization.getId() == null) {
            authorizationPO.setId(Long.valueOf(idGenerator.nextID()));
        } else {
            authorizationPO.setId(authorization.getId());
        }
        authorizationPO.setIdentifier(authorization.getIdentifier());
        authorizationPO.setUserId(authorization.getUserId());
        authorizationPO.setIdentityType(authorization.getIdentityType());
        return authorizationPO;
    }

    public Authorization createAuthorization(AuthorizationPO authorizationPO) {
        if (authorizationPO == null) {
            return null;
        }
        Authorization authorization = new Authorization();
        authorization.setCredential(authorizationPO.getCredential());
        authorization.setId(authorizationPO.getId());
        authorization.setIdentifier(authorizationPO.getIdentifier());
        authorization.setUserId(authorizationPO.getUserId());
        authorization.setIdentityType(authorizationPO.getIdentityType());
        return authorization;
    }
    public List<Authorization> createAuthorization(List<AuthorizationPO> authorizationPOs) {
        return authorizationPOs.stream().map(this::createAuthorization).collect(Collectors.toList());
    }
}
