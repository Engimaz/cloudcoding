package cn.edu.hcnu.auth.infrastructure.repository.impl;

import cn.edu.hcnu.auth.infrastructure.mapper.AuthorizationMapper;
import cn.edu.hcnu.auth.model.po.AuthorizationPO;
import cn.edu.hcnu.auth.infrastructure.repository.AuthorizationRepository;
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
public class AuthorizationRepositoryImpl extends ServiceImpl<AuthorizationMapper, AuthorizationPO> implements AuthorizationRepository {

}
