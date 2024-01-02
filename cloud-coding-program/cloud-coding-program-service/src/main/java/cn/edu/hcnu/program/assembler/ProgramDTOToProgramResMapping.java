package cn.edu.hcnu.program.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.program.model.dto.ProgramDTO;
import cn.edu.hcnu.program.model.dto.ProgramUserDTO;
import cn.edu.hcnu.program.model.res.ProgramRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProgramDTOToProgramResMapping extends IMapping<ProgramDTO, ProgramRes> {
}
