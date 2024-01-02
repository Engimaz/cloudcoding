package cn.edu.hcnu.manager.domain.event.feature;

import cn.edu.hcnu.manager.domain.event.feature.add.AddFeatureEvent;
import cn.edu.hcnu.manager.domain.event.feature.remove.RemoveFeatureEvent;
import cn.edu.hcnu.manager.domain.event.feature.update.UpdateFeatureEvent;
import cn.edu.hcnu.manager.domain.service.feature.Feature;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 13:41
 */
@Component
public class FeaturePublisher {
    private final ApplicationEventPublisher eventPublisher;

    public FeaturePublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishRemoveFeatureEvent(Long removeFeatureId) {
        eventPublisher.publishEvent(new RemoveFeatureEvent(this, removeFeatureId));
    }

    public void publishUpdateFeatureEvent(Feature feature) {
        eventPublisher.publishEvent(new UpdateFeatureEvent(this,feature));
    }

    public void publishAddFeatureEvent(Feature feature) {
        eventPublisher.publishEvent(new AddFeatureEvent(this,feature));
    }
}
