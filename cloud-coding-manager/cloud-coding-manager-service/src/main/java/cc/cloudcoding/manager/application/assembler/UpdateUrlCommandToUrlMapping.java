package cc.cloudcoding.manager.application.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.manager.domain.service.url.Url;
import cc.cloudcoding.manager.model.command.UpdateUrlCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface UpdateUrlCommandToUrlMapping extends IMapping<UpdateUrlCommand, Url> {
}
