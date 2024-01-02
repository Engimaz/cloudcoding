package cn.edu.hcnu.manager.domain.event.feature.update;

import cn.edu.hcnu.manager.domain.service.feature.Feature;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UpdateFeatureEvent extends ApplicationEvent {

    private Feature feature;

    public UpdateFeatureEvent(Object source, Feature feature) {
        super(source);
        this.feature = feature;
    }

}

