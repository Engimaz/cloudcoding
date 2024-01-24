package cc.cloudcoding.program.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.program.model.command.ExecuteCommand;
import cc.cloudcoding.program.model.req.ProgramReq;
import org.mapstruct.Mapper;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/15 1:58
 */
@Mapper(componentModel = "spring")
public interface ProgramReqToExecuteCommandMapping extends IMapping<ProgramReq, ExecuteCommand> {
}
