package cc.cloudcoding.program.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.program.domain.service.relation.ProgramUser;
import cc.cloudcoding.program.model.dto.ProgramUserDTO;
import org.mapstruct.Mapper;

@Mapper
public interface ProgramUserDTOToProgramUserMapping extends IMapping<ProgramUserDTO, ProgramUser> {
}
