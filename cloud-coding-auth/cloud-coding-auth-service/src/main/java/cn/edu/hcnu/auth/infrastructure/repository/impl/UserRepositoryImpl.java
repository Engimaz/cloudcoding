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
 *  服务实现类
 * </p>
 *
 * @author aichen
 * @since 2023-08-05 02:13:57
 */
@Service
public class UserRepositoryImpl extends ServiceImpl<UserMapper, UserPO> implements UserRepository {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public UserPO getUserByNickname(String nickname) {
        // 使用mybatis-plus的wrapper 查找字段为 nickname 的记录
        return  this.getOne(new LambdaQueryWrapper<UserPO>().eq(UserPO::getNickname, nickname));
    }





    @Override
    public List<PositionPO> getPositionByUserId(Long userId) {

        return null;
    }

    @Override
    public Page<User> query(Integer page, Integer size, String keyword) {
        Page<UserPO> pageOption = new Page<>(page, size);
        LambdaQueryWrapper<UserPO> lqw = new LambdaQueryWrapper<UserPO>();
        Optional.ofNullable(keyword)
                .ifPresent(k -> lqw.like(UserPO::getNickname, "%" + k + "%"));

        Page<UserPO> groupPOPage = this.getBaseMapper().selectPage(pageOption, lqw);
        List<User> groups = getUsers(groupPOPage.getRecords());
        Page<User> res = new Page();
        res.setRecords(groups);
        res.setTotal(groupPOPage.getTotal());
        return res;
    }

    @NotNull
    private List<User> getUsers(List<UserPO> list) {
        return list.stream().map(item -> {
            User bean = applicationContext.getBean(User.class);
            bean.setId(item.getId());
            bean.render();
            return bean;
        }).collect(Collectors.toList());
    }


}
