package cc.cloudcoding.auth.infrastructure.repository.impl;

import cc.cloudcoding.auth.infrastructure.mapper.OrganizationMapper;
import cc.cloudcoding.auth.infrastructure.repository.OrganizationRepository;
import cc.cloudcoding.auth.model.po.OrganizationPO;
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
public class OrganizationRepositoryImpl extends ServiceImpl<OrganizationMapper, OrganizationPO> implements OrganizationRepository {

}
