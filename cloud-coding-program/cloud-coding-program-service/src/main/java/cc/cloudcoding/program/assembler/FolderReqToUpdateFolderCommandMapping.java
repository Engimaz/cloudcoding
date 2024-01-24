package cc.cloudcoding.program.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.program.model.command.UpdateFolderCommand;
import cc.cloudcoding.program.model.req.FolderReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FolderReqToUpdateFolderCommandMapping extends IMapping<FolderReq, UpdateFolderCommand> {
}
