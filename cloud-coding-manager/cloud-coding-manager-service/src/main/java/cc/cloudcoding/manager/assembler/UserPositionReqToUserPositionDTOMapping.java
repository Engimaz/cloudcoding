package cc.cloudcoding.manager.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.manager.model.dto.UserPositionDTO;
import cc.cloudcoding.manager.model.req.UserPositionReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserPositionReqToUserPositionDTOMapping extends IMapping<UserPositionReq, UserPositionDTO> {
}
