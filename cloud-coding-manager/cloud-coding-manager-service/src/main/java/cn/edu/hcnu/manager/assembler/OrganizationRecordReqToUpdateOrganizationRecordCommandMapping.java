package cn.edu.hcnu.manager.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.manager.model.command.UpdateOrganizationRecordCommand;
import cn.edu.hcnu.manager.model.req.OrganizationRecordReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizationRecordReqToUpdateOrganizationRecordCommandMapping extends IMapping<OrganizationRecordReq, UpdateOrganizationRecordCommand> {
}
