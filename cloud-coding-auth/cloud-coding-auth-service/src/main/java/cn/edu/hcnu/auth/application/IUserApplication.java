package cn.edu.hcnu.auth.application;

import cn.edu.hcnu.auth.model.comand.AddUserCommand;
import cn.edu.hcnu.auth.model.comand.UpdateUserCommand;
import cn.edu.hcnu.auth.model.security.UserDTO;
import cn.edu.hcnu.base.model.CommonQuery;
import cn.edu.hcnu.base.model.PageDTO;

public interface IUserApplication {
    UserDTO getUserById(String id);

    UserDTO addUser(AddUserCommand addUserCommand);

    UserDTO updateUser(UpdateUserCommand resetPasswordCommand);

    PageDTO<UserDTO, CommonQuery> query(CommonQuery commonQuery);
}
