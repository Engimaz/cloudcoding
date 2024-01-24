package cc.cloudcoding.manager.domain.service.relation;

import cc.cloudcoding.manager.domain.service.relation.factorty.OrganizationRecordFactory;
import cc.cloudcoding.manager.infrastructure.repository.OrganizationRecordRepository;
import cc.cloudcoding.manager.model.po.OrganizationRecordPO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrganizationRecordDomainService {

    @Autowired
    OrganizationRecordFactory organizationRecordFactory;
    @Autowired
    OrganizationRecordRepository organizationRecordRepository;


    public OrganizationRecord queryById(Long id) {
        return organizationRecordFactory.createOrganizationRecord(organizationRecordRepository.getById(id));
    }

    public boolean removeById(Long id) {
        return organizationRecordRepository.removeById(id);
    }

    public OrganizationRecord save(OrganizationRecord organizationRecord) {
        OrganizationRecordPO organizationRecordPO = organizationRecordFactory.createOrganizationRecordPO(organizationRecord);
        boolean save = organizationRecordRepository.save(organizationRecordPO);
        if (save) {
            return organizationRecordFactory.createOrganizationRecord(organizationRecordPO);
        }
        return null;
    }

    public OrganizationRecord update(OrganizationRecord organizationRecord) {
        OrganizationRecordPO organizationRecordPO = organizationRecordFactory.createOrganizationRecordPO(organizationRecord);
        boolean update = organizationRecordRepository.updateById(organizationRecordPO);
        if (update) {
            return organizationRecordFactory.createOrganizationRecord(organizationRecordPO);
        }
        return null;
    }


    public List<OrganizationRecord> list(Integer page, Integer size, String keyword) {
        List<OrganizationRecord> collect = this.pageQuery(page, size, keyword).getRecords().stream().map((f) -> organizationRecordFactory.createOrganizationRecord(f)).collect(Collectors.toList());
        return collect;
    }

    public Long count(Integer page, Integer size, String keyword) {
        return this.pageQuery(page, size, keyword).getTotal();
    }

    private Page<OrganizationRecordPO> pageQuery(Integer page, Integer size, String keyword) {
        Page<OrganizationRecordPO> p1 = new Page<>(page, size);
        LambdaQueryWrapper<OrganizationRecordPO> wrapper = new LambdaQueryWrapper<OrganizationRecordPO>().like(keyword != null, OrganizationRecordPO::getContent, keyword);
        return organizationRecordRepository.getBaseMapper().selectPage(p1, wrapper);
    }

    public List<OrganizationRecord> list(Long userid) {
        return organizationRecordFactory.createOrganizationRecord(
                organizationRecordRepository.list(new LambdaQueryWrapper<OrganizationRecordPO>().eq(OrganizationRecordPO::getUserId, userid))
        );
    }

    public List<OrganizationRecord> listByOrganization(Long organizationId) {
        return organizationRecordFactory.createOrganizationRecord(
                organizationRecordRepository.list(new LambdaQueryWrapper<OrganizationRecordPO>().eq(OrganizationRecordPO::getOrganizationId, organizationId))
        );
    }
}
