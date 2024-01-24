package cc.cloudcoding.manager.application.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.manager.domain.service.url.Url;
import cc.cloudcoding.manager.model.command.AddUrlCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface AddUrlCommandToUrlMapping extends IMapping<AddUrlCommand, Url> {
}
