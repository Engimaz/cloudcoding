package cn.edu.hcnu.program.application.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.program.domain.service.file.File;
import cn.edu.hcnu.program.model.command.AddFileCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddFileCommandToFileMapping extends IMapping<AddFileCommand, File> {
}
