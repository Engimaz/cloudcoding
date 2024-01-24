package cc.cloudcoding.manager.application.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.manager.domain.service.feature.Feature;
import cc.cloudcoding.manager.model.command.AddFeatureCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddFeatureCommandToFeatureMapping extends IMapping<AddFeatureCommand, Feature> {
}
