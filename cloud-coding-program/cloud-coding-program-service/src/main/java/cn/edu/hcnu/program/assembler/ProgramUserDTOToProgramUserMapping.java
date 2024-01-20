package cn.edu.hcnu.program.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.program.domain.service.relation.ProgramUser;
import cn.edu.hcnu.program.model.dto.ProgramUserDTO;
import org.mapstruct.Mapper;

@Mapper
public interface ProgramUserDTOToProgramUserMapping extends IMapping<ProgramUserDTO, ProgramUser> {
}
