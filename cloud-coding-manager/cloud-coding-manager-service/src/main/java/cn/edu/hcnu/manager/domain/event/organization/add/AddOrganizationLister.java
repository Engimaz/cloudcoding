package cn.edu.hcnu.manager.domain.event.organization.add;

import cn.edu.hcnu.dictionary.rpc.DictionaryService;
import cn.edu.hcnu.id.domain.service.IDGenerator;
import cn.edu.hcnu.manager.domain.service.position.Position;
import cn.edu.hcnu.manager.domain.service.relation.UserPosition;
import cn.edu.hcnu.manager.infrastructure.repository.FeatureOrganizationRepository;
import cn.edu.hcnu.manager.infrastructure.repository.PositionRepository;
import cn.edu.hcnu.manager.infrastructure.repository.UserPositionRepository;
import cn.edu.hcnu.manager.model.po.FeatureOrganizationPO;
import cn.edu.hcnu.manager.model.po.PositionPO;
import cn.edu.hcnu.manager.model.po.UserPositionPO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddOrganizationLister {


    @Autowired
    private PositionRepository positionRepository;


    @Autowired
    private FeatureOrganizationRepository featureOrganizationRepository;

    @Autowired
    @Qualifier("snowflake")
    private IDGenerator idGenerator;

    @DubboReference(group = "dictionary")
    private DictionaryService dictionaryService;

    @Autowired
    private UserPositionRepository userPositionRepository;

    @EventListener
    public void handleCustomEvent(AddOrganizationEvent event) {

        if (event.getOrganization().getPositions() == null) return;

        List<Position> newPosition = new ArrayList<>(event.getOrganization().getPositions());

        // 新关系
        List<UserPosition> newUserPosition = new ArrayList<>(event.getOrganization().getUserPositions());
        for (Position position : newPosition) {
            for (UserPosition userPosition : newUserPosition) {
                if (userPosition.getPosition().equals(position.getValue())) {
                    userPosition.setPositionId((position.getId()));
                }
            }

        }

        List<PositionPO> newPoData = newPosition.stream().map(item -> {
            PositionPO positionPO = new PositionPO();
            positionPO.setId(item.getId());
            positionPO.setName(item.getName());
            positionPO.setValue(item.getValue());
            positionPO.setOrganizationId(item.getOrganizationId());
            positionPO.setStatus(dictionaryService.getDictionaryByValue(item.getStatus()).getId());
            return positionPO;
        }).collect(Collectors.toList());

        //  添加新职位
        positionRepository.saveBatch(newPoData);

        // 添加新职位和用户的关系
        List<UserPositionPO> collect1 = newUserPosition.stream().map(item -> {
            UserPositionPO userPositionPO = new UserPositionPO();
            if (item.getId() != null) {
                userPositionPO.setId(item.getId());
            } else {
                userPositionPO.setId(Long.valueOf(idGenerator.nextID()));
            }
            userPositionPO.setUserId(item.getUserId());
            userPositionPO.setPositionId(item.getPositionId());
            return userPositionPO;
        }).collect(Collectors.toList());

        userPositionRepository.saveBatch(collect1);
        if (event.getOrganization().getFeatures() != null) {
            //添加组织的功能
            List<FeatureOrganizationPO> collect = event.getOrganization().getFeatures().stream().map(f -> {
                FeatureOrganizationPO q = new FeatureOrganizationPO();
                q.setOrganizationId(event.getOrganization().getId());
                q.setFeatureId(f.getId());
                return q;
            }).collect(Collectors.toList());
            featureOrganizationRepository.saveBatch(collect);

        }

    }
}
