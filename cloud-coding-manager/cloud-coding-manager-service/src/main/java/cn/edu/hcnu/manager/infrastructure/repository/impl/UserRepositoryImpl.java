package cn.edu.hcnu.manager.infrastructure.repository.impl;

import cn.edu.hcnu.manager.infrastructure.mapper.UserMapper;
import cn.edu.hcnu.manager.infrastructure.repository.UserRepository;
import cn.edu.hcnu.manager.model.po.PositionPO;
import cn.edu.hcnu.manager.model.po.UserPO;
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
public class UserRepositoryImpl extends ServiceImpl<UserMapper, UserPO> implements UserRepository {

    @Override
    public UserPO getUserByNickname(String nickname) {
        // 使用mybatis-plus的wrapper 查找字段为 nickname 的记录
        return  this.getOne(new LambdaQueryWrapper<UserPO>().eq(UserPO::getNickname, nickname));
    }



    @Override
    public List<PositionPO> getPositionByUserId(Long userId) {

        return null;
    }


}
