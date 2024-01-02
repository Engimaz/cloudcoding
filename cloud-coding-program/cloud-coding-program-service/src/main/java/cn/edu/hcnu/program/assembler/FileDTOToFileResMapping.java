package cn.edu.hcnu.program.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.program.model.dto.FileDTO;
import cn.edu.hcnu.program.model.res.FileRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileDTOToFileResMapping extends IMapping<FileDTO, FileRes> {
}
