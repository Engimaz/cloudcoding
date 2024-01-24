package cc.cloudcoding.resource.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.resource.model.dto.ResourceDTO;
import cc.cloudcoding.resource.model.res.ResourceRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResourceDTOToResourceResMapping extends IMapping<ResourceDTO, ResourceRes> {
}
