package cc.cloudcoding.manager.infrastructure.repository.impl;

import cc.cloudcoding.manager.model.po.FeatureOrganizationPO;
import cc.cloudcoding.manager.infrastructure.mapper.FeatureOrganizationMapper;
import cc.cloudcoding.manager.infrastructure.repository.FeatureOrganizationRepository;
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
public class FeatureOrganizationRepositoryImpl extends ServiceImpl<FeatureOrganizationMapper, FeatureOrganizationPO> implements FeatureOrganizationRepository {

}
