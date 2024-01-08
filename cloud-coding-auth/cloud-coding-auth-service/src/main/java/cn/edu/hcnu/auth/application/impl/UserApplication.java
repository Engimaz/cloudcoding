package cn.edu.hcnu.auth.application.impl;

import cn.edu.hcnu.auth.application.IUserApplication;
import cn.edu.hcnu.auth.assembler.UserToUserDTOMapping;
import cn.edu.hcnu.auth.domain.service.authorization.Authorization;
import cn.edu.hcnu.auth.domain.service.user.User;
import cn.edu.hcnu.auth.infrastructure.repository.AuthorizationRepository;
import cn.edu.hcnu.auth.infrastructure.repository.UserRepository;
import cn.edu.hcnu.auth.model.comand.AddUserCommand;
import cn.edu.hcnu.auth.model.comand.ResetPasswordCommand;
import cn.edu.hcnu.auth.model.enums.LoginType;
import cn.edu.hcnu.auth.model.po.AuthorizationPO;
import cn.edu.hcnu.auth.model.po.UserPO;
import cn.edu.hcnu.auth.model.security.UserDTO;
import cn.edu.hcnu.base.model.CommonQuery;
import cn.edu.hcnu.base.model.PageDTO;
import cn.edu.hcnu.captcha.model.CodeType;
import cn.edu.hcnu.id.domain.service.IDGenerator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserApplication implements IUserApplication {


    private final UserToUserDTOMapping userToUserDTOMapping;


    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;


    private final UserRepository userRepository;

    private final ApplicationContext applicationContext;

    private final AuthorizationRepository authorizationRepository;

    @Autowired
    @Qualifier("snowflake")
    private IDGenerator idGenerator;

    @Autowired
    @Lazy
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public UserDTO getUserById(String id) {
        User bean = applicationContext.getBean(User.class);
        bean.setId(Long.parseLong(id));
        bean.render();
        UserDTO userDTO = userToUserDTOMapping.sourceToTarget(bean);

        // 查询邮箱
        List<Authorization> byUserId = authorizationRepository.list(new LambdaQueryWrapper<AuthorizationPO>().eq(AuthorizationPO::getUserId, bean.getId())).stream().map(item -> {
            Authorization authorization = applicationContext.getBean(Authorization.class);
            authorization.setIdentifier(item.getIdentifier());
            authorization.setUserId(item.getUserId());
            authorization.setId(item.getId());
            authorization.setIdentityType(item.getIdentityType());
            authorization.setCredential(item.getCredential());
            return authorization;
        }).collect(Collectors.toList());
        byUserId.forEach(item -> {
            if (item.getIdentityType().equals(LoginType.EMAIL.getValue())) {
                userDTO.setEmail(item.getIdentifier());
            }
            if (item.getIdentityType().equals(LoginType.PHONE.getValue())) {
                userDTO.setPhone(item.getIdentifier());
            }
        });

        return userDTO;
    }


    @Override
    public UserDTO addUser(AddUserCommand addUserCommand) {
        // 检验验证码是否正确
        String realCode = redisTemplate.opsForValue().get(CodeType.SignUp.getKey() + ":" + addUserCommand.getEmail());

        if (!Objects.equals(addUserCommand.getCode(), realCode)) {
            return null;
        }

        User bean = applicationContext.getBean(User.class);
        bean.setAvatar(addUserCommand.getAvatar());
        bean.setSex(Long.valueOf(addUserCommand.getSex()));
        bean.setNickname(addUserCommand.getNickname());
        bean.setStatus(0L);
        bean.setId(Long.valueOf(idGenerator.nextID()));
        bean.save();

        // 添加账号信息
        String sa = passwordEncoder.encode(addUserCommand.getPassword());
        // 构造昵称
        if (addUserCommand.getNickname() != null && !addUserCommand.getNickname().isEmpty()) {
            Authorization authorization = applicationContext.getBean(Authorization.class);
            authorization.setCredential(sa);
            authorization.setUserId(bean.getId());
            authorization.setIdentityType(LoginType.ACCOUNT.getValue());
            authorization.setIdentifier(addUserCommand.getNickname());
            authorization.save();
        }

        // 构造邮箱
        if (addUserCommand.getEmail() != null && !addUserCommand.getEmail().isEmpty()) {
            Authorization authorization = applicationContext.getBean(Authorization.class);
            authorization.setCredential(sa);
            authorization.setUserId(bean.getId());
            authorization.setIdentityType(LoginType.EMAIL.getValue());
            authorization.setIdentifier(addUserCommand.getEmail());
            authorization.save();
        }

        // 构造手机
        if (addUserCommand.getPhone() != null && !addUserCommand.getPhone().isEmpty()) {
            Authorization authorization = applicationContext.getBean(Authorization.class);
            authorization.setCredential(sa);
            authorization.setUserId(bean.getId());
            authorization.setIdentityType(LoginType.PHONE.getValue());
            authorization.setIdentifier(addUserCommand.getPhone());
            authorization.save();
        }


        return userToUserDTOMapping.sourceToTarget(bean);

    }


    @Override
    public UserDTO resetPassword(ResetPasswordCommand resetPasswordCommand) {
        // 检验验证码是否正确
        String realCode = redisTemplate.opsForValue().get(CodeType.Reset.getKey() + ":" + resetPasswordCommand.getEmail());

        if (!Objects.equals(resetPasswordCommand.getCode(), realCode)) {
            return null;
        }
        Authorization authorization = applicationContext.getBean(Authorization.class);
        authorization.setIdentifier(resetPasswordCommand.getEmail());
        authorization.renderByEmail();


        AuthorizationPO authorizationPO = new AuthorizationPO();
        authorizationPO.setUserId(authorization.getUserId());
        authorizationPO.setCredential(passwordEncoder.encode(resetPasswordCommand.getPassword()));
        boolean b = authorizationRepository.update(authorizationPO,
                new LambdaQueryWrapper<AuthorizationPO>().eq(AuthorizationPO::getUserId, authorization.getUserId()));
        if (b) {
            User bean = applicationContext.getBean(User.class);
            bean.setId(authorization.getId());
            bean.render();
            return userToUserDTOMapping.sourceToTarget(bean);
        }
        return null;
    }

    @Override
    public PageDTO<UserDTO, CommonQuery> query(CommonQuery query) {


        Page<UserPO> page = new Page<>(query.getPage(), query.getSize());
        LambdaQueryWrapper<UserPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(query.getKeyword() != null, UserPO::getNickname, query.getKeyword());

        Page<UserPO> res = userRepository.page(page, queryWrapper);

        List<User> collect = res.getRecords().stream().map(po -> {
            User bean = applicationContext.getBean(User.class);
            bean.setId(po.getId());
            bean.setNickname(po.getNickname());
            bean.setStatus(po.getStatus());
            bean.setAvatar(po.getAvatar());
            bean.setUpdateTime(po.getUpdateTime());
            bean.setCrateTime(po.getCreateTime());
            return bean;
        }).collect(Collectors.toList());
        return new PageDTO<>(userToUserDTOMapping.sourceToTarget(collect), page.getTotal(), query);

    }
}
