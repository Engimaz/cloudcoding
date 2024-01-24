package cc.cloudcoding.manager.application.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.manager.model.command.UpdateOrganizationRecordCommand;
import cc.cloudcoding.manager.domain.service.relation.OrganizationRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateOrganizationRecordCommandToOrganizationRecordMapping extends IMapping<UpdateOrganizationRecordCommand, OrganizationRecord> {
}
