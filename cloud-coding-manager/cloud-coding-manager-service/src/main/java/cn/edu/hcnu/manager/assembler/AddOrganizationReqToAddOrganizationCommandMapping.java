package cn.edu.hcnu.manager.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.manager.model.command.AddOrganizationCommand;
import cn.edu.hcnu.manager.model.req.OrganizationReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddOrganizationReqToAddOrganizationCommandMapping extends IMapping<OrganizationReq, AddOrganizationCommand> {
}
