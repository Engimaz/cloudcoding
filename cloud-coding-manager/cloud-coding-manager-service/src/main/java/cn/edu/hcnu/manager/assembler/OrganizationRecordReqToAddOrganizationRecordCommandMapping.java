package cn.edu.hcnu.manager.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.manager.model.command.AddOrganizationRecordCommand;
import cn.edu.hcnu.manager.model.req.OrganizationRecordReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizationRecordReqToAddOrganizationRecordCommandMapping extends IMapping<OrganizationRecordReq, AddOrganizationRecordCommand> {
}
