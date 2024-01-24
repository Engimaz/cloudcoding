package cc.cloudcoding.manager.application.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.manager.model.dto.FeatureOrganizationDTO;
import cc.cloudcoding.manager.domain.service.relation.FeatureOrganization;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeatureOrganizationToFeatureOrganizationDTOMapping extends IMapping<FeatureOrganization, FeatureOrganizationDTO> {
}
