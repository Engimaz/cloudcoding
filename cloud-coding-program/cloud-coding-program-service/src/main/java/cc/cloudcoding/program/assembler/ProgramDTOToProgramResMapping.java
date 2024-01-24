package cc.cloudcoding.program.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.program.model.dto.ProgramDTO;
import cc.cloudcoding.program.model.res.ProgramRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProgramDTOToProgramResMapping extends IMapping<ProgramDTO, ProgramRes> {
}
