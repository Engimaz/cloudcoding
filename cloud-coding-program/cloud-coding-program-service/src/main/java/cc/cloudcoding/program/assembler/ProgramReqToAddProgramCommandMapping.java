package cc.cloudcoding.program.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.program.model.command.AddProgramCommand;
import cc.cloudcoding.program.model.req.ProgramReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProgramReqToAddProgramCommandMapping extends IMapping<ProgramReq, AddProgramCommand> {
}
