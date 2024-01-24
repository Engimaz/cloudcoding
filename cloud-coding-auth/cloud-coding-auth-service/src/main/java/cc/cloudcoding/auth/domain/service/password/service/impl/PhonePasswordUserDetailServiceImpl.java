package cc.cloudcoding.auth.domain.service.password.service.impl;

import cc.cloudcoding.auth.infrastructure.mapper.AuthorizationMapper;
import cc.cloudcoding.auth.infrastructure.mapper.UserPositionMapper;
import cc.cloudcoding.auth.infrastructure.repository.UserRepository;
import cc.cloudcoding.auth.domain.service.password.service.PhonePasswordUserDetailService;
import cc.cloudcoding.auth.model.po.AuthorizationPO;
import cc.cloudcoding.auth.model.po.PositionPO;
import cc.cloudcoding.auth.model.po.UserPO;
import cc.cloudcoding.auth.model.security.SecurityUser;
import cc.cloudcoding.auth.model.enums.LoginType;
import cc.cloudcoding.base.model.CloudCodingConstant;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * 电话密码登录的UserDetailsService实现类
 *
 * @author 不才小马  邮箱:Aichen517@qq.com
 * @date 2023/08/05
 */
@Service
public class PhonePasswordUserDetailServiceImpl implements PhonePasswordUserDetailService {

    @Autowired
    private UserRepository userService;

    @Autowired
    private UserPositionMapper userPositionMapper;

    @Autowired
    private AuthorizationMapper authorizationMapper;

    @Override
    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
        AuthorizationPO authorizationPO = authorizationMapper.selectOne(new LambdaQueryWrapper<AuthorizationPO>().eq(AuthorizationPO::getIdentifier, mobile).eq(AuthorizationPO::getIdentityType, LoginType.PHONE.getValue()));

        UserPO userPO = userService.getOne(new LambdaQueryWrapper<UserPO>().eq(UserPO::getId, authorizationPO.getUserId()));

        if (Objects.isNull(userPO)) {
            throw new UsernameNotFoundException("用户不存在！");
        } else if (userPO.getStatus() == 1) {
            throw new UsernameNotFoundException("用户已被禁用！");
        }
        // 获取这个用户的职位信息
        List<PositionPO> positionPOS = userPositionMapper.selectPositionByUserId(userPO.getId());

        // 该用户的所有权限（职位）
        List<String> roles = new ArrayList<>();
        for (PositionPO positionPO : positionPOS) {
            roles.add(CloudCodingConstant.ROLE_PREFIX + positionPO.getValue());
        }


        // 将用户信息封装成SecurityUser对象
        return SecurityUser.builder()
                .userId(userPO.getId())
                .username(userPO.getNickname())
                .password(authorizationPO.getCredential())
                // 将角色放入authorities中
                .authorities(AuthorityUtils.createAuthorityList(ArrayUtil.toArray(roles, String.class)))
                .build();
    }
}
