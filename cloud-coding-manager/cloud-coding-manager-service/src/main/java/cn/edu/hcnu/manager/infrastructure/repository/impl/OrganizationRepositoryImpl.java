package cn.edu.hcnu.manager.infrastructure.repository.impl;

import cn.edu.hcnu.manager.infrastructure.mapper.OrganizationMapper;
import cn.edu.hcnu.manager.infrastructure.repository.OrganizationRepository;
import cn.edu.hcnu.manager.infrastructure.repository.PositionRepository;
import cn.edu.hcnu.manager.infrastructure.repository.UserPositionRepository;
import cn.edu.hcnu.manager.model.po.OrganizationPO;
import cn.edu.hcnu.manager.model.po.PositionPO;
import cn.edu.hcnu.manager.model.po.UserPositionPO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author aichen
 * @since 2023-08-05 02:13:57
 */
@Repository
public class OrganizationRepositoryImpl extends ServiceImpl<OrganizationMapper, OrganizationPO> implements OrganizationRepository {


    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private UserPositionRepository userPositionRepository;

    @Override
    public List<OrganizationPO> findByUserId(Long userId) {
        List<UserPositionPO> userPositionPOS = userPositionRepository.selectPositionByUserId(userId);
        // 对职位id去重
        List<Long> positionIds = userPositionPOS.stream()
                .map(UserPositionPO::getPositionId)
                .distinct() // 加上distinct()方法来去重
                .collect(Collectors.toList());
        List<PositionPO> positionPOS = positionRepository.listByIds(positionIds);
        // 对组织去重
        List<Long> organizationIds = positionPOS.stream()
                .map(PositionPO::getOrganizationId)
                .distinct()
                .collect(Collectors.toList());
        return listByIds(organizationIds);
    }
}
