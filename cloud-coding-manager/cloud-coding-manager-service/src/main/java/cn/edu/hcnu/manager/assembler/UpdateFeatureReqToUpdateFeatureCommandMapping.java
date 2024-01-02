package cn.edu.hcnu.manager.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.manager.model.command.UpdateFeatureCommand;
import cn.edu.hcnu.manager.model.req.FeatureReq;
import org.mapstruct.Mapper;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 15:17
 */
@Mapper(componentModel = "spring")
public interface UpdateFeatureReqToUpdateFeatureCommandMapping extends IMapping<FeatureReq, UpdateFeatureCommand> {
}
