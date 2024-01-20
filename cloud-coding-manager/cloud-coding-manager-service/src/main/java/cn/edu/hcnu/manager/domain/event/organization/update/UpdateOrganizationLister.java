package cn.edu.hcnu.manager.domain.event.organization.update;

import cn.edu.hcnu.dictionary.rpc.DictionaryService;
import cn.edu.hcnu.manager.infrastructure.repository.FeatureOrganizationRepository;
import cn.edu.hcnu.manager.infrastructure.repository.UserPositionRepository;
import cn.edu.hcnu.manager.model.po.FeatureOrganizationPO;
import cn.edu.hcnu.manager.model.po.PositionPO;
import cn.edu.hcnu.id.domain.service.IDGenerator;
import cn.edu.hcnu.manager.domain.service.position.Position;
import cn.edu.hcnu.manager.infrastructure.repository.PositionRepository;
import cn.edu.hcnu.manager.model.po.UserPositionPO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.dubbo.config.annotation.DubboReference;
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
    private FeatureOrganizationRepository featureOrganizationRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private UserPositionRepository userPositionRepository;

    @Autowired
    @Qualifier("snowflake")
    private IDGenerator idGenerator;
    @DubboReference(group = "dictionary")
    private DictionaryService dictionaryService;

    @EventListener
    public void handleCustomEvent(UpdateOrganizationEvent event) {

        List<Position> newPosition = new ArrayList<>(event.getOrganization().getPositions());


        List<UserPositionPO> newUserPosition = event.getOrganization().getUserPositions().stream().map(item -> {
            UserPositionPO userPositionPO = new UserPositionPO();
            userPositionPO.setPositionId(item.getPositionId());
            userPositionPO.setUserId(item.getUserId());
            userPositionPO.setId(item.getId());
            return userPositionPO;
        }).collect(Collectors.toList());

        // 删除所有的职位
        Optional.ofNullable(positionRepository.removeByOrganizationId(event.getOrganization().getId()))
                .ifPresent(positions -> {
                    // 删除用户与职位的关联
                    List<Long> positionIds = positions.stream()
                            .map(PositionPO::getId)
                            .collect(Collectors.toList());
                    userPositionRepository.remove(new LambdaQueryWrapper<UserPositionPO>().in(!positionIds.isEmpty(), UserPositionPO::getPositionId, positionIds));
                });
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
        userPositionRepository.saveBatch(newUserPosition);

        //删除组织的功能
        featureOrganizationRepository.remove(new LambdaQueryWrapper<FeatureOrganizationPO>().eq(FeatureOrganizationPO::getOrganizationId, event.getOrganization().getId()));
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
