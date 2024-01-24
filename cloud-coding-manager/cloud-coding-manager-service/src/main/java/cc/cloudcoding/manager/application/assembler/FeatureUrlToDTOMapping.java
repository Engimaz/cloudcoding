package cc.cloudcoding.manager.application.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.manager.model.dto.FeatureUrlDTO;
import cc.cloudcoding.manager.domain.service.relation.FeatureUrl;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeatureUrlToDTOMapping extends IMapping<FeatureUrl, FeatureUrlDTO> {
}
