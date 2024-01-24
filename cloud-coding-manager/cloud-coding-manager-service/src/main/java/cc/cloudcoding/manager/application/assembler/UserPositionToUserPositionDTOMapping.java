package cc.cloudcoding.manager.application.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.manager.domain.service.relation.UserPosition;
import cc.cloudcoding.manager.model.dto.UserPositionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserPositionToUserPositionDTOMapping extends IMapping<UserPosition, UserPositionDTO> {
}
