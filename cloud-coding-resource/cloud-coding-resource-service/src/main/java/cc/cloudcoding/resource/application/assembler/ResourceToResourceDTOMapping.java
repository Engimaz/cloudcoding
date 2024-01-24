package cc.cloudcoding.resource.application.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.resource.domain.service.resource.Resource;
import cc.cloudcoding.resource.model.dto.ResourceDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResourceToResourceDTOMapping extends IMapping<Resource, ResourceDTO> {
}
