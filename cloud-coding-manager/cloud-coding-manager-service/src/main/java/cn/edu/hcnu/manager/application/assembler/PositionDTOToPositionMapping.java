package cn.edu.hcnu.manager.application.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.manager.domain.service.position.Position;
import cn.edu.hcnu.manager.model.dto.PositionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PositionDTOToPositionMapping extends IMapping<PositionDTO, Position> {
}
