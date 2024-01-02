package cn.edu.hcnu.auth.assembler;

import cn.edu.hcnu.auth.domain.service.user.User;
import cn.edu.hcnu.auth.model.comand.AddUserCommand;
import cn.edu.hcnu.base.assembler.IMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddUserCommandToUserDOMapping extends IMapping<AddUserCommand, User> {
}
