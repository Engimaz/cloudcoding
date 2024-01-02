package cn.edu.hcnu.manager.domain.event.feature.update;

import cn.edu.hcnu.manager.domain.service.relation.FeatureUrlDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateFeatureListener {

    @Autowired
    private FeatureUrlDomainService featureUrlDomainService;


    @EventListener
    public void handleCustomEvent(UpdateFeatureEvent event) {
        // 移除功能与url的关系
        featureUrlDomainService.removeByFeatureId(event.getFeature().getId());

        // 添加功能与url的关系
        Optional.ofNullable(event.getFeature().getRelations()).ifPresent(relations -> {
            relations.forEach(relation -> relation.setFeatureId(event.getFeature().getId()));
            featureUrlDomainService.save(relations);
        });


    }
}
