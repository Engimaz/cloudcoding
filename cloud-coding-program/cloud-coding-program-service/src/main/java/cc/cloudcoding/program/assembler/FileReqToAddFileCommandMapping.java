package cc.cloudcoding.program.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.program.model.command.AddFileCommand;
import cc.cloudcoding.program.model.req.FileReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileReqToAddFileCommandMapping extends IMapping<FileReq , AddFileCommand> {
}
