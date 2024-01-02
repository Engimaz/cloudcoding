package cn.edu.hcnu.manager.application.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.manager.domain.service.url.Url;
import cn.edu.hcnu.manager.model.command.UpdateUrlCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface UpdateUrlCommandToUrlMapping extends IMapping<UpdateUrlCommand, Url> {
}
