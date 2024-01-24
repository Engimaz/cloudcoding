package cc.cloudcoding.program.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.program.model.command.AddFolderCommand;
import cc.cloudcoding.program.model.req.FolderReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FolderReqToAddFolderCommandMapping extends IMapping<FolderReq, AddFolderCommand> {
}
