package cc.cloudcoding.manager.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.manager.model.command.AddOrganizationRecordCommand;
import cc.cloudcoding.manager.model.req.OrganizationRecordReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizationRecordReqToAddOrganizationRecordCommandMapping extends IMapping<OrganizationRecordReq, AddOrganizationRecordCommand> {
}
