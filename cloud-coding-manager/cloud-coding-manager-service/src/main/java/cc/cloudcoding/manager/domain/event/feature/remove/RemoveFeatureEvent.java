package cc.cloudcoding.manager.domain.event.feature.remove;

import org.springframework.context.ApplicationEvent;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 13:46
 */
public class RemoveFeatureEvent extends ApplicationEvent {

    private Long featureId;

    public RemoveFeatureEvent(Object source, Long featureId) {
        super(source);
        this.featureId = featureId;
    }

    public Long getFeatureId() {
        return this.featureId;
    }
}

