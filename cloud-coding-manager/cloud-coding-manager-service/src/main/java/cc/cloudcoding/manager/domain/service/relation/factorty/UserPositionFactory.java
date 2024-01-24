package cc.cloudcoding.manager.domain.service.relation.factorty;

import cc.cloudcoding.id.domain.service.IDGenerator;
import cc.cloudcoding.manager.domain.service.relation.UserPosition;
import cc.cloudcoding.manager.model.po.UserPositionPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/8 23:16
 */

@Component
public class UserPositionFactory {

    @Autowired
    @Qualifier(value = "snowflake")
    private IDGenerator idGenerator;

    public UserPosition createUserPosition(Long userId, Long positionId) {
        UserPosition userPosition = new UserPosition();
        userPosition.setUserId(userId);
        userPosition.setPositionId(positionId);
        userPosition.setId(Long.valueOf(idGenerator.nextID()));
        return userPosition;
    }

    public UserPosition createUserPosition(UserPositionPO userPositionPO) {
        UserPosition userPosition = new UserPosition();
        userPosition.setId(userPositionPO.getId());
        userPosition.setUserId(userPositionPO.getUserId());
        userPosition.setPositionId(userPositionPO.getPositionId());
        return userPosition;
    }

    public UserPositionPO createUserPositionPO(UserPosition userPosition) {
        UserPositionPO userPositionPO = new UserPositionPO();
        if (userPosition.getId() != null) {
            userPositionPO.setId(userPosition.getId());
        } else {
            userPositionPO.setId(Long.valueOf(idGenerator.nextID()));
        }
        userPositionPO.setUserId(userPosition.getUserId());
        userPositionPO.setPositionId(userPosition.getPositionId());
        return userPositionPO;
    }

    public Collection<UserPositionPO> createUserPositionPO(List<UserPosition> objects) {
        return objects.stream().map(this::createUserPositionPO).collect(Collectors.toList());
    }

    public List<UserPosition> createUserPosition(List<UserPositionPO> list) {
        return list.stream().map(this::createUserPosition).collect(Collectors.toList());
    }
}
