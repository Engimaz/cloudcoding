package cc.cloudcoding.manager.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.manager.model.command.AddUrlCommand;
import cc.cloudcoding.manager.model.req.AddUrlReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddUrlReqToAddUrlCommandMapping extends IMapping<AddUrlReq, AddUrlCommand> {
}
