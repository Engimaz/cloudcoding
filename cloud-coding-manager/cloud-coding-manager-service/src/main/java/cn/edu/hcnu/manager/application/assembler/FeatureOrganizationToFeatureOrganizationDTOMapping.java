package cn.edu.hcnu.manager.application.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.manager.domain.service.relation.FeatureOrganization;
import cn.edu.hcnu.manager.model.dto.FeatureOrganizationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeatureOrganizationToFeatureOrganizationDTOMapping extends IMapping<FeatureOrganization, FeatureOrganizationDTO> {
}
