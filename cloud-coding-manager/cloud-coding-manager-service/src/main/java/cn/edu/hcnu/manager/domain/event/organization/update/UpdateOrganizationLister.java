package cn.edu.hcnu.manager.domain.event.organization.update;

import cn.edu.hcnu.manager.model.po.PositionPO;
import cn.edu.hcnu.id.domain.service.IDGenerator;
import cn.edu.hcnu.manager.domain.service.position.Position;
import cn.edu.hcnu.manager.domain.service.relation.FeatureOrganization;
import cn.edu.hcnu.manager.domain.service.relation.FeatureOrganizationDomainService;
import cn.edu.hcnu.manager.domain.service.relation.UserPosition;
import cn.edu.hcnu.manager.domain.service.relation.UserPositionDomainService;
import cn.edu.hcnu.manager.infrastructure.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/13 15:27
 */
@Component
public class UpdateOrganizationLister {


    @Autowired
    private UserPositionDomainService userPositionDomainService;

    @Autowired
    private FeatureOrganizationDomainService featureOrganizationDomainService;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    @Qualifier("snowflake")
    private IDGenerator idGenerator;

    @EventListener
    public void handleCustomEvent(UpdateOrganizationEvent event) {

        List<Position> newPosition = new ArrayList<>(event.getOrganization().getPositions());

        List<UserPosition> newUserPosition = new ArrayList<>(event.getOrganization().getUserPositions());
        for (Position position : newPosition) {
            String positionId = idGenerator.nextID();
            for (UserPosition userPosition : newUserPosition) {
                if (userPosition.getPosition().equals(position.getValue())) {
                    userPosition.setPositionId(Long.valueOf(positionId));
                }
            }
            position.setId(Long.valueOf(positionId));
            position.setOrganizationId(event.getOrganization().getId());
        }


        // 删除所有的职位
        Optional.ofNullable(positionRepository.removeByOrganizationId(event.getOrganization().getId()))
                .ifPresent(positions -> {
                    // 删除用户与职位的关联
                    List<Long> positionIds = positions.stream()
                            .map(PositionPO::getId)
                            .collect(Collectors.toList());
                    userPositionDomainService.removeByPositionId(positionIds);
                });
        List<PositionPO> newPoData = newPosition.stream().map(item -> {
            PositionPO positionPO = new PositionPO();
            positionPO.setId(item.getId());
            positionPO.setName(item.getName());
            positionPO.setValue(item.getValue());
            positionPO.setOrganizationId(item.getOrganizationId());
            positionPO.setStatus(Long.valueOf(item.getStatus()));
            return positionPO;
        }).collect(Collectors.toList());

        //  添加新职位
        positionRepository.saveBatch(newPoData);
        // 添加新职位和用户的关系
        userPositionDomainService.save(newUserPosition);


        //删除组织的功能
        featureOrganizationDomainService.removeByOrganizationId(event.getOrganization().getId());
        //添加组织的功能
        List<FeatureOrganization> collect = event.getOrganization().getFeatures().stream().map(f -> {
            FeatureOrganization q = new FeatureOrganization();
            q.setOrganizationId(event.getOrganization().getId());
            q.setFeatureId(f.getId());
            return q;
        }).collect(Collectors.toList());

        featureOrganizationDomainService.batchSave(collect);
    }

}
