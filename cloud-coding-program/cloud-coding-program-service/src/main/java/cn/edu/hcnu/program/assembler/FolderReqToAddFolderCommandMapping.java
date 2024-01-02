package cn.edu.hcnu.program.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.program.model.command.AddFolderCommand;
import cn.edu.hcnu.program.model.req.FolderReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FolderReqToAddFolderCommandMapping extends IMapping<FolderReq, AddFolderCommand> {
}
