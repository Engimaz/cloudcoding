package cn.edu.hcnu.manager.application.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.manager.domain.service.feature.Feature;
import cn.edu.hcnu.manager.model.dto.FeatureDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeatureToDTOMapping extends IMapping<Feature, FeatureDTO> {
}
