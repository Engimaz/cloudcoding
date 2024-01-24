package cc.cloudcoding.dictionary.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.dictionary.model.req.GroupReq;
import cc.cloudcoding.dictionary.model.command.UpdateGroupCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupReqToUpdateGroupCommandMapping extends IMapping<GroupReq, UpdateGroupCommand> {


}
