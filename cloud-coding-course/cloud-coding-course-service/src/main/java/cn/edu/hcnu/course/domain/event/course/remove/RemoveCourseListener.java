package cn.edu.hcnu.course.domain.event.course.remove;

import cn.edu.hcnu.course.domain.service.unit.UnitDomainService;
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

public class RemoveCourseListener {

    @Autowired
    private UnitDomainService domainService;

    @EventListener
    public void handleCustomEvent(RemoveCourseEvent event) {

        Optional.ofNullable(domainService.getByCourseId(event.getRemoveId())).ifPresent(units ->
                units.forEach(unit -> domainService.delete(unit.getId()))
        );

    }
}
