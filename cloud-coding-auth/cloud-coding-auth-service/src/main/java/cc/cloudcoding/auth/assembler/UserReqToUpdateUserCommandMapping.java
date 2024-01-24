package cc.cloudcoding.auth.assembler;

import cc.cloudcoding.auth.model.comand.UpdateUserCommand;
import cc.cloudcoding.auth.model.req.UserReq;
import cc.cloudcoding.base.assembler.IMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserReqToUpdateUserCommandMapping extends IMapping<UserReq, UpdateUserCommand> {
}
