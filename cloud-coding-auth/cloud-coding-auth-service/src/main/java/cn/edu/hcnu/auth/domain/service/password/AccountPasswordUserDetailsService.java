package cn.edu.hcnu.auth.domain.service.password;


import cn.edu.hcnu.auth.infrastructure.mapper.AuthorizationMapper;
import cn.edu.hcnu.auth.infrastructure.mapper.UserPositionMapper;
import cn.edu.hcnu.auth.model.po.AuthorizationPO;
import cn.edu.hcnu.auth.model.po.PositionPO;
import cn.edu.hcnu.auth.model.po.UserPO;
import cn.edu.hcnu.auth.infrastructure.repository.UserRepository;
import cn.edu.hcnu.auth.model.security.SecurityUser;
import cn.edu.hcnu.auth.model.enums.LoginType;
import cn.edu.hcnu.base.model.CloudCodingConstant;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 *  * 账号密码登录的UserDetailsService实现类
 *
 * @author 不才小马  邮箱:Aichen517@qq.com
 * @date 2023/08/05
 */
@Service
public class AccountPasswordUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepository userService;

    @Autowired
    private UserPositionMapper userPositionMapper;

    @Autowired
    private AuthorizationMapper authorizationMapper;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        AuthorizationPO authorizationPO = authorizationMapper.selectOne(new LambdaQueryWrapper<AuthorizationPO>().eq(AuthorizationPO::getIdentifier, nickname).eq(AuthorizationPO::getIdentityType, LoginType.ACCOUNT.getValue()));
        if (Objects.isNull(authorizationPO)) {
            throw new UsernameNotFoundException("用户不存在！");
        }
        UserPO userPO = userService.getOne(new LambdaQueryWrapper<UserPO>().eq(UserPO::getId, authorizationPO.getUserId()));


        if (Objects.isNull(userPO)) {
            throw new UsernameNotFoundException("用户不存在！");
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
                .accountLocked(userPO.getStatus() == 1)
                .authorities(AuthorityUtils.createAuthorityList(ArrayUtil.toArray(roles, String.class)))
                .build();
    }
}
