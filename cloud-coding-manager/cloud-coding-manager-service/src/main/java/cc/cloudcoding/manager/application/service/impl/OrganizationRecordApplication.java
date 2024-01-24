/*
 * @Description: 
 * @Version: 2.0
 * @Author: AICHEN
 * @Date: 2023-10-26 00:04:08
 * @LastEditors: AICHEN
 * @LastEditTime: 2023-10-28 18:09:50
 */
package cc.cloudcoding.manager.application.service.impl;

import cc.cloudcoding.auth.interfaces.UserApi;
import cc.cloudcoding.auth.model.res.UserRes;
import cc.cloudcoding.base.model.CommonQuery;
import cc.cloudcoding.base.model.PageDTO;
import cc.cloudcoding.base.model.RestResponse;
import cc.cloudcoding.manager.application.assembler.AddOrganizationRecordCommandToOrganizationRecordMapping;
import cc.cloudcoding.manager.model.aggr.OrganizationRecordAggregate;
import cc.cloudcoding.manager.model.command.AddOrganizationRecordCommand;
import cc.cloudcoding.manager.model.command.UpdateOrganizationRecordCommand;
import cc.cloudcoding.manager.model.dto.OrganizationRecordDTO;
import cc.cloudcoding.manager.application.assembler.OrganizationRecordToOrganizationRecordDTOMapping;
import cc.cloudcoding.manager.application.assembler.UpdateOrganizationRecordCommandToOrganizationRecordMapping;
import cc.cloudcoding.manager.application.service.IOrganizationRecordApplication;
import cc.cloudcoding.manager.assembler.OrganizationRecordDTOToOrganizationRecordResMapping;
import cc.cloudcoding.manager.domain.service.relation.OrganizationRecord;
import cc.cloudcoding.manager.domain.service.relation.OrganizationRecordDomainService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganizationRecordApplication implements IOrganizationRecordApplication {

    @Autowired
    private OrganizationRecordDomainService organizationRecordDomainService;

    @Autowired
    private OrganizationRecordToOrganizationRecordDTOMapping organizationRecordToOrganizationRecordDTOMapping;

    @Autowired
    private AddOrganizationRecordCommandToOrganizationRecordMapping addOrganizationRecordCommandToOrganizationRecordMapping;

    @Autowired
    private UpdateOrganizationRecordCommandToOrganizationRecordMapping updateOrganizationRecordCommandToOrganizationRecordMapping;

    @Override
    public OrganizationRecordDTO getById(Long id) {
        OrganizationRecord record = organizationRecordDomainService.queryById(id);
        return organizationRecordToOrganizationRecordDTOMapping.sourceToTarget(record);
    }

    @Override
    public boolean deleteById(Long id) {
        return organizationRecordDomainService.removeById(id);
    }

    @Override
    public OrganizationRecordDTO save(AddOrganizationRecordCommand addOrganizationCommand) {
        OrganizationRecord organizationRecord = addOrganizationRecordCommandToOrganizationRecordMapping
                .sourceToTarget(addOrganizationCommand);
        organizationRecord = organizationRecordDomainService.save(organizationRecord);
        return organizationRecordToOrganizationRecordDTOMapping.sourceToTarget(organizationRecord);
    }

    @Override
    public OrganizationRecordDTO update(UpdateOrganizationRecordCommand updateOrganizationRecordCommand) {
        OrganizationRecord organizationRecord = updateOrganizationRecordCommandToOrganizationRecordMapping
                .sourceToTarget(updateOrganizationRecordCommand);
        organizationRecord = organizationRecordDomainService.update(organizationRecord);
        return organizationRecordToOrganizationRecordDTOMapping.sourceToTarget(organizationRecord);
    }

    @Override
    public PageDTO<OrganizationRecordDTO, CommonQuery> list(CommonQuery commonQuery) {
        List<OrganizationRecord> list = organizationRecordDomainService.list(commonQuery.getPage(),
                commonQuery.getSize(), commonQuery.getKeyword());
        List<OrganizationRecordDTO> list1 = organizationRecordToOrganizationRecordDTOMapping.sourceToTarget(list);
        Long count = organizationRecordDomainService.count(commonQuery.getPage(), commonQuery.getSize(),
                commonQuery.getKeyword());
        return new PageDTO<>(list1, count, commonQuery);
    }

    @Override
    public List<OrganizationRecordDTO> list(Long userid) {
        return organizationRecordToOrganizationRecordDTOMapping
                .sourceToTarget(organizationRecordDomainService.list(userid));
    }

    @DubboReference(group = "user")
    private UserApi userApi;

    @Autowired
    private OrganizationRecordDTOToOrganizationRecordResMapping organizationRecordDTOToOrganizationRecordResMapping;

    @Override
    public OrganizationRecordAggregate listByOrganizationId(Long organizationId) {
        OrganizationRecordAggregate organizationRecordAggregate = new OrganizationRecordAggregate();
        List<OrganizationRecord> organizationRecords = organizationRecordDomainService
                .listByOrganization(organizationId);
        organizationRecordAggregate.setRecords(organizationRecordDTOToOrganizationRecordResMapping
                .sourceToTarget(organizationRecordToOrganizationRecordDTOMapping.sourceToTarget(organizationRecords)));
        // 获得里面的用户id
        List<String> collect = organizationRecords.stream().map(OrganizationRecord::getUserId).distinct()
                .collect(Collectors.toList());
        List<UserRes> userRes = collect.stream().map(userApi::getUserInfo).map(RestResponse::getResult)
                .collect(Collectors.toList());
        organizationRecordAggregate.setUsers(userRes);

        return organizationRecordAggregate;

    }
}
