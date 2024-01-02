package cn.edu.hcnu.manager.infrastructure.repository.impl;

import cn.edu.hcnu.manager.infrastructure.mapper.OrganizationRecordMapper;
import cn.edu.hcnu.manager.infrastructure.repository.OrganizationRecordRepository;
import cn.edu.hcnu.manager.model.po.OrganizationRecordPO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

@Repository
public class OrganizationRecordRepositoryImpl extends ServiceImpl<OrganizationRecordMapper, OrganizationRecordPO> implements OrganizationRecordRepository {
}
