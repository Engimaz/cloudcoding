package cn.edu.hcnu.manager.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.manager.model.command.AddUrlCommand;
import cn.edu.hcnu.manager.model.req.AddUrlReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddUrlReqToAddUrlCommandMapping extends IMapping<AddUrlReq, AddUrlCommand> {
}
