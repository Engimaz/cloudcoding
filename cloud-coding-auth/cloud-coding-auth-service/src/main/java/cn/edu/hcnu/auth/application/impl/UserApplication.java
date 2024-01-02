package cn.edu.hcnu.auth.application.impl;

import cn.edu.hcnu.auth.application.IUserApplication;
import cn.edu.hcnu.auth.assembler.UserToUserDTOMapping;
import cn.edu.hcnu.auth.domain.service.authorization.Authorization;
import cn.edu.hcnu.auth.domain.service.authorization.AuthorizationDomainService;
import cn.edu.hcnu.auth.domain.service.user.User;
import cn.edu.hcnu.auth.infrastructure.repository.UserRepository;
import cn.edu.hcnu.auth.model.comand.AddUserCommand;
import cn.edu.hcnu.auth.model.comand.ResetPasswordCommand;
import cn.edu.hcnu.auth.model.enums.LoginType;
import cn.edu.hcnu.auth.model.security.UserDTO;
import cn.edu.hcnu.base.model.CommonQuery;
import cn.edu.hcnu.base.model.PageDTO;
import cn.edu.hcnu.captcha.model.CodeType;
import cn.edu.hcnu.id.domain.service.IDGenerator;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserApplication implements IUserApplication {



    @Autowired
    private UserToUserDTOMapping userToUserDTOMapping;


    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorizationDomainService authorizationDomainService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    @Qualifier("snowflake")
    private IDGenerator idGenerator;


    @Override
    public UserDTO getUserById(String id) {
        User bean = applicationContext.getBean(User.class);
        bean.setId(Long.parseLong(id));
        bean.render();
        UserDTO userDTO = userToUserDTOMapping.sourceToTarget(bean);

        // 查询邮箱
        List<Authorization> byUserId = authorizationDomainService.getByUserId(bean.getId());
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

    @Autowired
    @Lazy
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public UserDTO addUser(AddUserCommand addUserCommand) {
        // 检验验证码是否正确
        String realCode = redisTemplate.opsForValue().get(CodeType.SignUp.getKey() + ":" + addUserCommand.getEmail());

        if (!Objects.equals(addUserCommand.getCode(), realCode)) {
            return null;
        }

        User bean = applicationContext.getBean(User.class);
        bean.setAvatar(addUserCommand.getAvatar());
        bean.setStatus(Long.valueOf(addUserCommand.getSex()));
        bean.setNickname(addUserCommand.getNickname());
        bean.setStatus(addUserCommand.getStatus());
        bean.setId(Long.valueOf(idGenerator.nextID()));
        bean.save();

        // 添加账号信息
        String sa = passwordEncoder.encode(addUserCommand.getPassword());
        // 构造昵称
        if (addUserCommand.getNickname() != null && !addUserCommand.getNickname().isEmpty()) {
            Authorization authorization = new Authorization();
            authorization.setCredential(sa);
            authorization.setUserId(bean.getId());
            authorization.setIdentityType(LoginType.ACCOUNT.getValue());
            authorization.setIdentifier(addUserCommand.getNickname());
            authorizationDomainService.addAuthorization(authorization);
        }

        // 构造邮箱
        if (addUserCommand.getEmail() != null && !addUserCommand.getEmail().isEmpty()) {
            Authorization authorization = new Authorization();
            authorization.setCredential(sa);
            authorization.setUserId(bean.getId());
            authorization.setIdentityType(LoginType.EMAIL.getValue());
            authorization.setIdentifier(addUserCommand.getEmail());
            authorizationDomainService.addAuthorization(authorization);
        }

        // 构造手机
        if (addUserCommand.getPhone() != null && !addUserCommand.getPhone().isEmpty()) {
            Authorization authorization = new Authorization();
            authorization.setCredential(sa);
            authorization.setUserId(bean.getId());
            authorization.setIdentityType(LoginType.PHONE.getValue());
            authorization.setIdentifier(addUserCommand.getPhone());
            authorizationDomainService.addAuthorization(authorization);
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

        // 用户id 进行更新
        Authorization authorization = authorizationDomainService.getAuthorizationByEmail(resetPasswordCommand.getEmail());

        Authorization updateData = new Authorization();
        updateData.setCredential(passwordEncoder.encode(resetPasswordCommand.getPassword()));
        updateData.setUserId(authorization.getUserId());
        boolean b = authorizationDomainService.updatePassword(updateData);
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
        Page<User> page = userRepository.query(query.getPage(), query.getSize(), query.getKeyword());

        return new PageDTO<>(userToUserDTOMapping.sourceToTarget(page.getRecords()), page.getTotal(),query);


    }
}
