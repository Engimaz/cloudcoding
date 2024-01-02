package cn.edu.hcnu.course.domain.event.unit;

import cn.edu.hcnu.course.domain.event.unit.add.AddUnitEvent;
import cn.edu.hcnu.course.domain.event.unit.remove.RemoveUnitEvent;
import cn.edu.hcnu.course.domain.service.unit.Unit;
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
