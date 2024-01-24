package cc.cloudcoding.auth.assembler;

import cc.cloudcoding.auth.model.comand.AddUserCommand;
import cc.cloudcoding.auth.model.req.UserReq;
import cc.cloudcoding.base.assembler.IMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserReqToAddUserCommandMapping extends IMapping<UserReq, AddUserCommand> {
}
