package cc.cloudcoding.manager.infrastructure.repository.impl;

import cc.cloudcoding.manager.model.po.PositionPO;
import cc.cloudcoding.manager.infrastructure.mapper.PositionMapper;
import cc.cloudcoding.manager.infrastructure.repository.PositionRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author aichen
 * @since 2023-08-05 02:13:57
 */
@Repository
public class PositionRepositoryImpl extends ServiceImpl<PositionMapper, PositionPO> implements PositionRepository {

    @Override
    public List<PositionPO> removeByOrganizationId(Long id) {
        List<PositionPO> list = queryByOrganizationId(id);
        LambdaQueryWrapper<PositionPO> eq = new LambdaQueryWrapper<PositionPO>().eq(PositionPO::getOrganizationId, id);
        this.remove(eq);
        return list;
    }

    @Override
    public List<PositionPO> queryByOrganizationId(Long id) {
        LambdaQueryWrapper<PositionPO> eq = new LambdaQueryWrapper<PositionPO>().eq(PositionPO::getOrganizationId, id);
        List<PositionPO> list = this.list(eq);
        return list;
    }
}
