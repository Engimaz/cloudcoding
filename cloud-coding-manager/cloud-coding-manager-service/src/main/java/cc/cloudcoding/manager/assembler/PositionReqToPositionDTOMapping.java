package cc.cloudcoding.manager.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.manager.model.dto.PositionDTO;
import cc.cloudcoding.manager.model.req.PositionReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PositionReqToPositionDTOMapping extends IMapping<PositionReq, PositionDTO> {
}
