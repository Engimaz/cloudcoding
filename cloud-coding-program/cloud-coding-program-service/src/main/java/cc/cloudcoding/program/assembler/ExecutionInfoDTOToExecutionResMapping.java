package cc.cloudcoding.program.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.program.model.dto.ExecutionInfoDTO;
import cc.cloudcoding.program.model.res.ExecutionInfoRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExecutionInfoDTOToExecutionResMapping extends IMapping<ExecutionInfoDTO, ExecutionInfoRes> {
}
