package cn.edu.hcnu.manager.infrastructure.repository.impl;

import cn.edu.hcnu.manager.infrastructure.mapper.UserPositionMapper;
import cn.edu.hcnu.manager.infrastructure.repository.UserPositionRepository;
import cn.edu.hcnu.manager.model.po.UserPositionPO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author aichen
 * @since 2023-08-05 02:13:57
 */
@Repository
public class UserPositionRepositoryImpl extends ServiceImpl<UserPositionMapper, UserPositionPO> implements UserPositionRepository {

    @Override
    public List<UserPositionPO> selectPositionByUserId(Long userId) {
        return list(new LambdaQueryWrapper<UserPositionPO>().eq(UserPositionPO::getUserId, userId));
    }
}
