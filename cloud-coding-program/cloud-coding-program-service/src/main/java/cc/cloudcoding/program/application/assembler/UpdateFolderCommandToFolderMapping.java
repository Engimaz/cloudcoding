package cc.cloudcoding.program.application.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.program.domain.service.folder.Folder;
import cc.cloudcoding.program.model.command.UpdateFolderCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateFolderCommandToFolderMapping extends IMapping<UpdateFolderCommand, Folder> {
}
