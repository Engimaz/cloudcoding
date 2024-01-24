package cc.cloudcoding.program.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.program.model.dto.FolderDTO;
import cc.cloudcoding.program.model.res.FolderRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FolderDTOToFolderResMapping extends IMapping<FolderDTO, FolderRes> {
}
