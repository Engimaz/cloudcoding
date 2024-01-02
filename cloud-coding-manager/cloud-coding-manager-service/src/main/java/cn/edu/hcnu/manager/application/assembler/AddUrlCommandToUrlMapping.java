package cn.edu.hcnu.manager.application.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.manager.domain.service.url.Url;
import cn.edu.hcnu.manager.model.command.AddUrlCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface AddUrlCommandToUrlMapping extends IMapping<AddUrlCommand, Url> {
}
