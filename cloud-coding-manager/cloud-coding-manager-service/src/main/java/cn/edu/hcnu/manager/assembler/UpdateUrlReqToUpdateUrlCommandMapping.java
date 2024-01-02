package cn.edu.hcnu.manager.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.manager.model.command.UpdateUrlCommand;
import cn.edu.hcnu.manager.model.req.UpdateUrlReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateUrlReqToUpdateUrlCommandMapping extends IMapping<UpdateUrlReq, UpdateUrlCommand> {
}
