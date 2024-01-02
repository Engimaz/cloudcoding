package cn.edu.hcnu.program.application.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.program.domain.service.folder.Folder;
import cn.edu.hcnu.program.model.command.UpdateFolderCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateFolderCommandToFolderMapping extends IMapping<UpdateFolderCommand, Folder> {
}
