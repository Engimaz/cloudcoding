package cc.cloudcoding.manager.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.manager.model.command.UpdateFeatureCommand;
import cc.cloudcoding.manager.model.req.FeatureReq;
import org.mapstruct.Mapper;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 15:17
 */
@Mapper(componentModel = "spring")
public interface UpdateFeatureReqToUpdateFeatureCommandMapping extends IMapping<FeatureReq, UpdateFeatureCommand> {
}
