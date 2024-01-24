package cc.cloudcoding.manager.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.manager.model.dto.OrganizationRecordDTO;
import cc.cloudcoding.manager.model.res.OrganizationRecordRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizationRecordDTOToOrganizationRecordResMapping extends IMapping<OrganizationRecordDTO, OrganizationRecordRes> {
}
