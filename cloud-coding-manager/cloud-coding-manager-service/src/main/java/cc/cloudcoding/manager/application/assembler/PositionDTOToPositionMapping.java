package cc.cloudcoding.manager.application.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.manager.domain.service.position.Position;
import cc.cloudcoding.manager.model.dto.PositionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PositionDTOToPositionMapping extends IMapping<PositionDTO, Position> {
}
