package cn.edu.hcnu.manager.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.manager.model.dto.OrganizationRecordDTO;
import cn.edu.hcnu.manager.model.res.OrganizationRecordRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizationRecordDTOToOrganizationRecordResMapping extends IMapping<OrganizationRecordDTO, OrganizationRecordRes> {
}
