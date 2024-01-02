package cn.edu.hcnu.program.application.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.program.domain.service.program.Program;
import cn.edu.hcnu.program.model.dto.ProgramDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProgramToProgramDTOMapping extends IMapping<Program, ProgramDTO> {
}
