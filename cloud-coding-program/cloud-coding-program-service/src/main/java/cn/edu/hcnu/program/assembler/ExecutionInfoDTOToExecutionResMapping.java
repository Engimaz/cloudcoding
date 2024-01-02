package cn.edu.hcnu.program.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.program.model.dto.ExecutionInfoDTO;
import cn.edu.hcnu.program.model.res.ExecutionInfoRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExecutionInfoDTOToExecutionResMapping extends IMapping<ExecutionInfoDTO, ExecutionInfoRes> {
}
