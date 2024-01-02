package cn.edu.hcnu.manager.domain.service.relation;

import cn.edu.hcnu.manager.domain.service.relation.factorty.UserPositionFactory;
import cn.edu.hcnu.manager.infrastructure.repository.UserPositionRepository;
import cn.edu.hcnu.manager.model.po.UserPositionPO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/8 23:15
 */
@Component
public class UserPositionDomainService {

    @Autowired
    private UserPositionRepository userPositionRepository;

    @Autowired
    private UserPositionFactory userPositionFactory;

    public boolean save(UserPosition userPosition) {
        UserPositionPO userPositionPO = userPositionFactory.createUserPositionPO(userPosition);
        return userPositionRepository.save(userPositionPO);
    }

    public boolean removeByPositionId(List<Long> collect) {
        return userPositionRepository.remove(new LambdaQueryWrapper<UserPositionPO>().in(UserPositionPO::getPositionId, collect));
    }

    public boolean save(List<UserPosition> objects) {
        return userPositionRepository.saveBatch(userPositionFactory.createUserPositionPO(objects));
    }

    public List<UserPosition> queryByPositionId(Long id) {
        return userPositionFactory.createUserPosition(userPositionRepository.list(new LambdaQueryWrapper<UserPositionPO>().eq(UserPositionPO::getPositionId, id)));
    }


    public List<UserPosition> queryByUserId(Long id) {
        return userPositionFactory.createUserPosition(userPositionRepository.list(new LambdaQueryWrapper<UserPositionPO>().eq(UserPositionPO::getUserId, id)));

    }
}
