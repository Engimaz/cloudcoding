package cn.edu.hcnu.manager.domain.event.feature.add;

import cn.edu.hcnu.manager.domain.service.relation.FeatureUrlDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddFeatureListener {

    @Autowired
    private FeatureUrlDomainService featureUrlDomainService;


    @EventListener
    public void handleCustomEvent(AddFeatureEvent event) {

        // 添加功能与url的关系
        Optional.ofNullable(event.getFeature().getRelations()).ifPresent(relations -> {
            relations.forEach(relation -> relation.setFeatureId(event.getFeature().getId()));
            featureUrlDomainService.save(relations);
        });



    }
}
