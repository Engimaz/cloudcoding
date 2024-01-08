package cn.edu.hcnu.auth.infrastructure.repository.impl;

import cn.edu.hcnu.auth.domain.service.user.User;
import cn.edu.hcnu.auth.infrastructure.mapper.UserMapper;
import cn.edu.hcnu.auth.model.po.PositionPO;
import cn.edu.hcnu.auth.model.po.UserPO;
import cn.edu.hcnu.auth.infrastructure.repository.UserRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
