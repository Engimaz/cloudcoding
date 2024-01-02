package cn.edu.hcnu.auth.assembler;

import cn.edu.hcnu.auth.model.security.UserDTO;
import cn.edu.hcnu.auth.model.res.UserRes;
import cn.edu.hcnu.base.assembler.IMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDTOToUserResMapping extends IMapping<UserDTO, UserRes> {
}
