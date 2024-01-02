package cn.edu.hcnu.course.domain.event.unit.add;

import cn.edu.hcnu.course.domain.event.unit.UnitPublisher;
import cn.edu.hcnu.course.domain.service.unit.Unit;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class AddUnitEvent extends ApplicationEvent {

    private Unit unit;

    public AddUnitEvent(UnitPublisher source, Unit unit) {
        super(source);
        this.unit = unit;
    }
}
