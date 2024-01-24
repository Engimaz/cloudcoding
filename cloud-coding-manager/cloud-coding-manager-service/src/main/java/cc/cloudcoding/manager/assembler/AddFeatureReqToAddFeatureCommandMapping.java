package cc.cloudcoding.manager.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.manager.model.command.AddFeatureCommand;
import cc.cloudcoding.manager.model.req.FeatureReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface AddFeatureReqToAddFeatureCommandMapping extends IMapping<FeatureReq, AddFeatureCommand> {
}
