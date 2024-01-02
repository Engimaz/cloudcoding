package cn.edu.hcnu.program.application.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.program.domain.service.folder.Folder;
import cn.edu.hcnu.program.model.command.AddFolderCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddFolderCommandToFolderMapping extends IMapping<AddFolderCommand, Folder> {
}
