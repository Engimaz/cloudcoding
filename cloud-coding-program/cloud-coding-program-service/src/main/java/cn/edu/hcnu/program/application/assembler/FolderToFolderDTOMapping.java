package cn.edu.hcnu.program.application.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.program.domain.service.folder.Folder;
import cn.edu.hcnu.program.model.dto.FolderDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FolderToFolderDTOMapping extends IMapping<Folder, FolderDTO> {
}
