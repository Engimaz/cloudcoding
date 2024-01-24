package cc.cloudcoding.manager.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.manager.model.command.AddOrganizationCommand;
import cc.cloudcoding.manager.model.req.OrganizationReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddOrganizationReqToAddOrganizationCommandMapping extends IMapping<OrganizationReq, AddOrganizationCommand> {
}
