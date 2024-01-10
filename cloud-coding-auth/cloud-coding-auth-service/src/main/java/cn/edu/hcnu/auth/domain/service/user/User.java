package cn.edu.hcnu.auth.domain.service.user;

import cn.edu.hcnu.auth.infrastructure.repository.UserRepository;
import cn.edu.hcnu.auth.model.po.UserPO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class User {
    private Long id;
    private String nickname;
    private String avatar;
    private Long sex;
    private Long status;
    private String idnumber;
    private LocalDateTime crateTime;
    private LocalDateTime updateTime;



    @Autowired
    private UserRepository userRepository;

    public void render() {
        UserPO byId = userRepository.getById(id);
        setAvatar(byId.getAvatar());
        setNickname(byId.getNickname());
        setSex(byId.getSex());
        setStatus(byId.getStatus());
        setCrateTime(byId.getCreateTime());
        setIdnumber(byId.getIdnumber());
        setUpdateTime(byId.getUpdateTime());

    }

    public  void save() {
        UserPO userPO = new UserPO();
        userPO.setAvatar(this.avatar);
        userPO.setSex(this.sex);
        userPO.setNickname(this.nickname);
        userPO.setStatus(this.status);
        userPO.setId(this.id);
        userPO.setIdnumber(this.idnumber);
        userRepository.save(userPO);

    }

    public void update() {
        UserPO userPO = new UserPO();
        userPO.setAvatar(this.avatar);
        userPO.setSex(this.sex);
        userPO.setNickname(this.nickname);
        userPO.setStatus(this.status);
        userPO.setId(this.id);
        userPO.setIdnumber(this.idnumber);
        userRepository.updateById(userPO);
    }
}
