package cn.edu.hcnu.manager.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.manager.model.dto.OrganizationDTO;
import cn.edu.hcnu.manager.model.res.OrganizationRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizationDTOToOrganizationResMapping extends IMapping<OrganizationDTO, OrganizationRes> {
}
