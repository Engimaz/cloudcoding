package cc.cloudcoding.auth.infrastructure.repository.impl;

import cc.cloudcoding.auth.infrastructure.mapper.UserMapper;
import cc.cloudcoding.auth.infrastructure.repository.UserRepository;
import cc.cloudcoding.auth.model.po.UserPO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author aichen
 * @since 2023-08-05 02:13:57
 */
@Service
public class UserRepositoryImpl extends ServiceImpl<UserMapper, UserPO> implements UserRepository {

}
