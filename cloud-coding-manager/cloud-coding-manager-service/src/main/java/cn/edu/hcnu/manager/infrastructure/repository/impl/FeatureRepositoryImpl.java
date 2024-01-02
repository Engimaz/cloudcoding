package cn.edu.hcnu.manager.infrastructure.repository.impl;

import cn.edu.hcnu.manager.infrastructure.mapper.FeatureMapper;
import cn.edu.hcnu.manager.infrastructure.repository.FeatureRepository;
import cn.edu.hcnu.manager.model.po.FeaturePO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author aichen
 * @since 2023-08-05 02:13:57
 */
@Repository
public class FeatureRepositoryImpl extends ServiceImpl<FeatureMapper, FeaturePO> implements FeatureRepository {

}
