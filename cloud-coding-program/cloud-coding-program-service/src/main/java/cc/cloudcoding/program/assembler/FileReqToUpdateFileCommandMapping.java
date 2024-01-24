package cc.cloudcoding.program.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.program.model.command.UpdateFileCommand;
import cc.cloudcoding.program.model.req.FileReq;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface FileReqToUpdateFileCommandMapping extends IMapping<FileReq, UpdateFileCommand> {
}
