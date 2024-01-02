package cn.edu.hcnu.resource.application.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.resource.domain.service.resource.Resource;
import cn.edu.hcnu.resource.model.dto.ResourceDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResourceToResourceDTOMapping extends IMapping<Resource, ResourceDTO> {
}
