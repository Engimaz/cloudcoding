package cn.edu.hcnu.manager.application.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.manager.domain.service.relation.OrganizationRecord;
import cn.edu.hcnu.manager.model.dto.OrganizationRecordDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizationRecordToOrganizationRecordDTOMapping extends IMapping<OrganizationRecord, OrganizationRecordDTO> {
}
