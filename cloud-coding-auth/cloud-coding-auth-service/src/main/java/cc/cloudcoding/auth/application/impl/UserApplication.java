package cc.cloudcoding.auth.application.impl;

import cc.cloudcoding.auth.application.IUserApplication;
import cc.cloudcoding.auth.assembler.UserToUserDTOMapping;
import cc.cloudcoding.auth.domain.service.authorization.Authorization;
import cc.cloudcoding.auth.domain.service.user.User;
import cc.cloudcoding.auth.infrastructure.repository.AuthorizationRepository;
import cc.cloudcoding.auth.infrastructure.repository.UserRepository;
import cc.cloudcoding.auth.model.comand.AddUserCommand;
import cc.cloudcoding.auth.model.comand.UpdateUserCommand;
import cc.cloudcoding.auth.model.enums.LoginType;
import cc.cloudcoding.auth.model.po.AuthorizationPO;
import cc.cloudcoding.auth.model.po.UserPO;
import cc.cloudcoding.auth.model.security.UserDTO;
import cc.cloudcoding.base.model.CommonQuery;
import cc.cloudcoding.base.model.PageDTO;
import cc.cloudcoding.captcha.model.CodeType;
import cc.cloudcoding.id.domain.service.IDGenerator;
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
        bean.setIdnumber(addUserCommand.getIdnumber());
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
    public UserDTO updateUser(UpdateUserCommand updateUserCommand) {
        // 检验验证码是否正确
        String realCode = redisTemplate.opsForValue().get(CodeType.Reset.getKey() + ":" + updateUserCommand.getEmail());

        if (!Objects.equals(updateUserCommand.getCode(), realCode)) {
            return null;
        }

        User bean = applicationContext.getBean(User.class);
        bean.setAvatar(updateUserCommand.getAvatar());
        bean.setSex(Long.valueOf(updateUserCommand.getSex()));
        bean.setNickname(updateUserCommand.getNickname());
        bean.setStatus(updateUserCommand.getStatus());
        bean.setId(Long.valueOf(idGenerator.nextID()));
        bean.update();


        Authorization authorization = applicationContext.getBean(Authorization.class);
        authorization.setIdentifier(updateUserCommand.getEmail());
        authorization.renderByEmail();


        AuthorizationPO authorizationPO = new AuthorizationPO();
        authorizationPO.setUserId(authorization.getUserId());
        authorizationPO.setCredential(passwordEncoder.encode(updateUserCommand.getPassword()));

        boolean b = authorizationRepository.update(authorizationPO,
                new LambdaQueryWrapper<AuthorizationPO>().eq(AuthorizationPO::getUserId, authorization.getUserId()));
        if (b) {
            User bean1 = applicationContext.getBean(User.class);
            bean1.setId(authorization.getId());
            bean1.render();
            return userToUserDTOMapping.sourceToTarget(bean1);
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
            bean.setSex(po.getSex());
            bean.setAvatar(po.getAvatar());
            bean.setIdnumber(po.getIdnumber());
            bean.setUpdateTime(po.getUpdateTime());
            bean.setCrateTime(po.getCreateTime());
            return bean;
        }).collect(Collectors.toList());
        List<UserDTO> userDTOS = userToUserDTOMapping.sourceToTarget(collect);
        for (UserDTO userDTO : userDTOS) {
            LambdaQueryWrapper<AuthorizationPO> eq = new LambdaQueryWrapper<AuthorizationPO>().eq(AuthorizationPO::getUserId, userDTO.getId()).eq(AuthorizationPO::getIdentityType, LoginType.EMAIL.getValue());
            AuthorizationPO one = authorizationRepository.getOne(eq);
            userDTO.setEmail(one.getIdentifier());
        }
        return new PageDTO<>(userDTOS, page.getTotal(), query);

    }
}
