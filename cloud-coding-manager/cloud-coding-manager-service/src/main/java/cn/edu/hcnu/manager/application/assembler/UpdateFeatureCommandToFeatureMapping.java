package cn.edu.hcnu.manager.application.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.manager.domain.service.feature.Feature;
import cn.edu.hcnu.manager.model.command.UpdateFeatureCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateFeatureCommandToFeatureMapping extends IMapping<UpdateFeatureCommand, Feature> {
}
