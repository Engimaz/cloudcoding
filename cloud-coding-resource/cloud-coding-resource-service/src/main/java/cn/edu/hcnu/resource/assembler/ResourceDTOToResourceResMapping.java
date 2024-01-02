package cn.edu.hcnu.resource.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.resource.model.dto.ResourceDTO;
import cn.edu.hcnu.resource.model.res.ResourceRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResourceDTOToResourceResMapping extends IMapping<ResourceDTO, ResourceRes> {
}
