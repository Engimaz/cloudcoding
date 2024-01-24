package cc.cloudcoding.auth.application;

import cc.cloudcoding.auth.model.comand.AddUserCommand;
import cc.cloudcoding.auth.model.comand.UpdateUserCommand;
import cc.cloudcoding.auth.model.security.UserDTO;
import cc.cloudcoding.base.model.CommonQuery;
import cc.cloudcoding.base.model.PageDTO;

public interface IUserApplication {
    UserDTO getUserById(String id);

    UserDTO addUser(AddUserCommand addUserCommand);

    UserDTO updateUser(UpdateUserCommand resetPasswordCommand);

    PageDTO<UserDTO, CommonQuery> query(CommonQuery commonQuery);
}
