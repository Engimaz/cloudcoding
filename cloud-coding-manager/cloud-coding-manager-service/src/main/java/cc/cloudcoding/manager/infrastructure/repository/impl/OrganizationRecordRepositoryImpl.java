package cc.cloudcoding.manager.infrastructure.repository.impl;

import cc.cloudcoding.manager.model.po.OrganizationRecordPO;
import cc.cloudcoding.manager.infrastructure.mapper.OrganizationRecordMapper;
import cc.cloudcoding.manager.infrastructure.repository.OrganizationRecordRepository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

@Repository
public class OrganizationRecordRepositoryImpl extends ServiceImpl<OrganizationRecordMapper, OrganizationRecordPO> implements OrganizationRecordRepository {
}
