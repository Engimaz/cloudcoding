package cn.edu.hcnu.program.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.program.model.command.UpdateFolderCommand;
import cn.edu.hcnu.program.model.req.FolderReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FolderReqToUpdateFolderCommandMapping extends IMapping<FolderReq, UpdateFolderCommand> {
}
