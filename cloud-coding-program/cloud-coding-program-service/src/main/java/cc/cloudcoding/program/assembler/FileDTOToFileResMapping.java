package cc.cloudcoding.program.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.program.model.dto.FileDTO;
import cc.cloudcoding.program.model.res.FileRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileDTOToFileResMapping extends IMapping<FileDTO, FileRes> {
}
