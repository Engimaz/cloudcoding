package cc.cloudcoding.manager.application.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.manager.domain.service.feature.Feature;
import cc.cloudcoding.manager.model.dto.FeatureDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeatureToDTOMapping extends IMapping<Feature, FeatureDTO> {
}
