package cc.cloudcoding.program.application.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.program.domain.service.program.Program;
import cc.cloudcoding.program.model.command.UpdateProgramCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateProgramCommandToProgramMapping extends IMapping<UpdateProgramCommand, Program> {
}
