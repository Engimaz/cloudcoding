package cn.edu.hcnu.manager.application.service;

import cn.edu.hcnu.base.model.CommonQuery;
import cn.edu.hcnu.base.model.PageDTO;
import cn.edu.hcnu.manager.model.aggr.OrganizationRecordAggregate;
import cn.edu.hcnu.manager.model.command.AddOrganizationRecordCommand;
import cn.edu.hcnu.manager.model.command.UpdateOrganizationRecordCommand;
import cn.edu.hcnu.manager.model.dto.OrganizationRecordDTO;

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
