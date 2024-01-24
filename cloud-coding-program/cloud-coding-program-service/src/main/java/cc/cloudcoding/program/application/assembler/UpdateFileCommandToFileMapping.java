package cc.cloudcoding.program.application.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.program.domain.service.file.File;
import cc.cloudcoding.program.model.command.UpdateFileCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateFileCommandToFileMapping extends IMapping<UpdateFileCommand, File> {
}
