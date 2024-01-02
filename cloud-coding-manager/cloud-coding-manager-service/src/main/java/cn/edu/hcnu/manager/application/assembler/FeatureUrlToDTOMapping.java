package cn.edu.hcnu.manager.application.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.manager.domain.service.relation.FeatureUrl;
import cn.edu.hcnu.manager.model.dto.FeatureUrlDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeatureUrlToDTOMapping extends IMapping<FeatureUrl, FeatureUrlDTO> {
}
