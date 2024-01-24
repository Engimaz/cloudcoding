package cc.cloudcoding.manager.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.manager.model.dto.OrganizationDTO;
import cc.cloudcoding.manager.model.res.OrganizationRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizationDTOToOrganizationResMapping extends IMapping<OrganizationDTO, OrganizationRes> {
}
