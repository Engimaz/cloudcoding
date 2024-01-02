package cn.edu.hcnu.program.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.program.model.command.AddFileCommand;
import cn.edu.hcnu.program.model.req.FileReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileReqToAddFileCommandMapping extends IMapping<FileReq , AddFileCommand> {
}
