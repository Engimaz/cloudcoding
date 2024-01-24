package cc.cloudcoding.manager.infrastructure.repository.impl;

import cc.cloudcoding.manager.model.po.FeatureUrlPO;
import cc.cloudcoding.manager.infrastructure.mapper.FeatureUrlMapper;
import cc.cloudcoding.manager.infrastructure.repository.FeatureUrlRepository;
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
public class FeatureUrlRepositoryImpl extends ServiceImpl<FeatureUrlMapper, FeatureUrlPO> implements FeatureUrlRepository {

    @Override
    public List<FeatureUrlPO> queryByFeatureId(Long id) {
        LambdaQueryWrapper<FeatureUrlPO> eq = new LambdaQueryWrapper<FeatureUrlPO>().eq(FeatureUrlPO::getFeatureId, id);
        return this.list(eq);
    }
}
