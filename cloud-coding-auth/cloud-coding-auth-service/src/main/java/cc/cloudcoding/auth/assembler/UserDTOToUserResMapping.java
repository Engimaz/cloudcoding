package cc.cloudcoding.auth.assembler;

import cc.cloudcoding.auth.model.security.UserDTO;
import cc.cloudcoding.auth.model.res.UserRes;
import cc.cloudcoding.base.assembler.IMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDTOToUserResMapping extends IMapping<UserDTO, UserRes> {
}
