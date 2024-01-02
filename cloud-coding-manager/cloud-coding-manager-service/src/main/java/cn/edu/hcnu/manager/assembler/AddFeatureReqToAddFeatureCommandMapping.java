package cn.edu.hcnu.manager.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.manager.model.command.AddFeatureCommand;
import cn.edu.hcnu.manager.model.req.FeatureReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface AddFeatureReqToAddFeatureCommandMapping extends IMapping<FeatureReq, AddFeatureCommand> {
}
