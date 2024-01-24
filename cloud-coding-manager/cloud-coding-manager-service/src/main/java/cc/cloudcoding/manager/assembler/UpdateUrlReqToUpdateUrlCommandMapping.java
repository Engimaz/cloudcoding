package cc.cloudcoding.manager.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.manager.model.command.UpdateUrlCommand;
import cc.cloudcoding.manager.model.req.UpdateUrlReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateUrlReqToUpdateUrlCommandMapping extends IMapping<UpdateUrlReq, UpdateUrlCommand> {
}
