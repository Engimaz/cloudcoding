package cc.cloudcoding.auth.infrastructure.repository.impl;

import cc.cloudcoding.auth.infrastructure.mapper.FeatureOrganizationMapper;
import cc.cloudcoding.auth.infrastructure.repository.FeatureOrganizationRepository;
import cc.cloudcoding.auth.model.po.FeatureOrganizationPO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author aichen
 * @since 2023-08-05 02:13:57
 */
@Service
public class FeatureOrganizationRepositoryImpl extends ServiceImpl<FeatureOrganizationMapper, FeatureOrganizationPO> implements FeatureOrganizationRepository {

}
