package cn.edu.hcnu.program.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.program.model.command.ExecuteCommand;
import cn.edu.hcnu.program.model.req.ProgramReq;
import org.mapstruct.Mapper;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/15 1:58
 */
@Mapper(componentModel = "spring")
public interface ProgramReqToExecuteCommandMapping extends IMapping<ProgramReq, ExecuteCommand> {
}
