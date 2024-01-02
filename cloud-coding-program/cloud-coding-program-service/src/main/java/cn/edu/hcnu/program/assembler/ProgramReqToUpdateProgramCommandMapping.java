package cn.edu.hcnu.program.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.program.model.command.UpdateProgramCommand;
import cn.edu.hcnu.program.model.req.ProgramReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProgramReqToUpdateProgramCommandMapping extends IMapping<ProgramReq, UpdateProgramCommand> {
}
