package cn.edu.hcnu.manager.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.manager.model.dto.UserPositionDTO;
import cn.edu.hcnu.manager.model.req.UserPositionReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserPositionReqToUserPositionDTOMapping extends IMapping<UserPositionReq, UserPositionDTO> {
}
