package cn.edu.hcnu.manager.domain.event.feature.add;

import cn.edu.hcnu.manager.domain.service.feature.Feature;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AddFeatureEvent extends ApplicationEvent {

    private Feature feature;

    public AddFeatureEvent(Object source, Feature feature) {
        super(source);
        this.feature = feature;
    }

}
