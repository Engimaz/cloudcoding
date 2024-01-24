package cc.cloudcoding.auth.assembler;

import cc.cloudcoding.auth.domain.service.user.User;
import cc.cloudcoding.auth.model.comand.AddUserCommand;
import cc.cloudcoding.base.assembler.IMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddUserCommandToUserDOMapping extends IMapping<AddUserCommand, User> {
}
