package cc.cloudcoding.course.domain.event.unit;

import cc.cloudcoding.course.domain.event.unit.add.AddUnitEvent;
import cc.cloudcoding.course.domain.event.unit.remove.RemoveUnitEvent;
import cc.cloudcoding.course.domain.service.unit.Unit;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 13:41
 */
@Component
public class UnitPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public UnitPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishRemoveUnitEvent(String removeId) {
        eventPublisher.publishEvent(new RemoveUnitEvent(this, removeId));
    }

    public void publishAddUnitEvent(Unit unit) {
        eventPublisher.publishEvent(new AddUnitEvent(this, unit));
    }
}
