package cc.cloudcoding.manager.application.service;

import cc.cloudcoding.base.model.CommonQuery;
import cc.cloudcoding.base.model.PageDTO;
import cc.cloudcoding.manager.model.aggr.OrganizationRecordAggregate;
import cc.cloudcoding.manager.model.command.AddOrganizationRecordCommand;
import cc.cloudcoding.manager.model.command.UpdateOrganizationRecordCommand;
import cc.cloudcoding.manager.model.dto.OrganizationRecordDTO;

import java.util.List;

public interface IOrganizationRecordApplication {
    OrganizationRecordDTO getById(Long id);

    boolean deleteById(Long id);

    OrganizationRecordDTO save(AddOrganizationRecordCommand addOrganizationCommand);

    OrganizationRecordDTO update(UpdateOrganizationRecordCommand updateOrganizationRecordCommand);

    PageDTO<OrganizationRecordDTO, CommonQuery> list(CommonQuery commonQuery);

    List<OrganizationRecordDTO> list(Long userid);

    OrganizationRecordAggregate listByOrganizationId(Long organizationId);
}
