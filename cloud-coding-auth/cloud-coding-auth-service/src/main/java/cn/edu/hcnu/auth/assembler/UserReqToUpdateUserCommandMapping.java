package cn.edu.hcnu.auth.assembler;

import cn.edu.hcnu.auth.model.comand.UpdateUserCommand;
import cn.edu.hcnu.auth.model.req.UserReq;
import cn.edu.hcnu.base.assembler.IMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserReqToUpdateUserCommandMapping extends IMapping<UserReq, UpdateUserCommand> {
}
