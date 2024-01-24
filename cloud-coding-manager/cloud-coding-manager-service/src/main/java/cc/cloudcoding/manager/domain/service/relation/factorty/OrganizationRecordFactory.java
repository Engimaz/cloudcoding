package cc.cloudcoding.manager.domain.service.relation.factorty;


import cc.cloudcoding.id.domain.service.IDGenerator;
import cc.cloudcoding.manager.model.po.OrganizationRecordPO;
import cc.cloudcoding.manager.domain.service.relation.OrganizationRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrganizationRecordFactory {


    @Autowired
    @Qualifier(value = "snowflake")
    private IDGenerator idGenerator;

    public OrganizationRecord createOrganizationRecord(OrganizationRecordPO po) {
        OrganizationRecord organizationRecord = new OrganizationRecord();
        organizationRecord.setOrganizationId(String.valueOf(po.getOrganizationId()));
        organizationRecord.setId(String.valueOf(po.getId()));
        organizationRecord.setContent(po.getContent());
        organizationRecord.setUserId(String.valueOf(po.getUserId()));
        return organizationRecord;
    }

    public OrganizationRecordPO createOrganizationRecordPO(OrganizationRecord po) {
        OrganizationRecordPO organizationRecord = new OrganizationRecordPO();
        organizationRecord.setOrganizationId(Long.valueOf(po.getOrganizationId()));
        if (po.getId() == null) {
            organizationRecord.setId(Long.valueOf(idGenerator.nextID()));
        } else {
            organizationRecord.setId(Long.valueOf(po.getId()));
        }
        organizationRecord.setContent(po.getContent());
        organizationRecord.setUserId(Long.valueOf(po.getUserId()));
        return organizationRecord;
    }

    public List<OrganizationRecord> createOrganizationRecord(List<OrganizationRecordPO> list) {
        return list.stream().map(this::createOrganizationRecord).collect(Collectors.toList());
    }
}
