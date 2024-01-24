package cc.cloudcoding.manager.infrastructure.repository.impl;

import cc.cloudcoding.manager.model.po.AuthorizationPO;
import cc.cloudcoding.manager.infrastructure.mapper.AuthorizationMapper;
import cc.cloudcoding.manager.infrastructure.repository.AuthorizationRepository;
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
public class AuthorizationRepositoryImpl extends ServiceImpl<AuthorizationMapper, AuthorizationPO> implements AuthorizationRepository {

}
