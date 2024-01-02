package cn.edu.hcnu.program.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.program.model.command.UpdateFileCommand;
import cn.edu.hcnu.program.model.req.FileReq;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface FileReqToUpdateFileCommandMapping extends IMapping<FileReq, UpdateFileCommand> {
}
