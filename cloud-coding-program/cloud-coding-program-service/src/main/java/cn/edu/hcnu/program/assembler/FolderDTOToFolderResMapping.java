package cn.edu.hcnu.program.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.program.model.dto.FolderDTO;
import cn.edu.hcnu.program.model.res.FolderRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FolderDTOToFolderResMapping extends IMapping<FolderDTO, FolderRes> {
}
