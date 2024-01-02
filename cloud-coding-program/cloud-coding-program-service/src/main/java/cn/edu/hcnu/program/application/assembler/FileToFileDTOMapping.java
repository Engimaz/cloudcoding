package cn.edu.hcnu.program.application.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.program.domain.service.file.File;
import cn.edu.hcnu.program.model.dto.FileDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileToFileDTOMapping extends IMapping<File, FileDTO> {
}
