package cn.edu.hcnu.manager.application.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.manager.domain.service.relation.UserPosition;
import cn.edu.hcnu.manager.model.dto.UserPositionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserPositionToUserPositionDTOMapping extends IMapping<UserPosition, UserPositionDTO> {
}
