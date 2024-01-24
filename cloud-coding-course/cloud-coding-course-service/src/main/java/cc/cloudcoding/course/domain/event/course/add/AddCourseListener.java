package cc.cloudcoding.course.domain.event.course.add;

import cc.cloudcoding.course.domain.service.unit.UnitDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 13:46
 */
@Service

public class AddCourseListener {

    @Autowired
    private UnitDomainService domainService;

    @EventListener
    public void handleCustomEvent(AddCourseEvent event) {
        // 添加他的单元
        Optional.ofNullable(event.getCourse().getUnits()).ifPresent(units ->
                units.forEach(unit -> {
                    unit.setCourseId(event.getCourse().getId());
                    domainService.save(unit);
                })
        );

    }
}
