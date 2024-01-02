package cn.edu.hcnu.manager.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.manager.model.dto.PositionDTO;
import cn.edu.hcnu.manager.model.req.PositionReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PositionReqToPositionDTOMapping extends IMapping<PositionReq, PositionDTO> {
}
