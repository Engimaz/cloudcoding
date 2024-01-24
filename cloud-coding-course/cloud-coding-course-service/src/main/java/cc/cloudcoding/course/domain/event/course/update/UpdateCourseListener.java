package cc.cloudcoding.course.domain.event.course.update;

import cc.cloudcoding.course.domain.service.unit.Unit;
import cc.cloudcoding.course.domain.service.unit.UnitDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 13:46
 */
@Service

public class UpdateCourseListener {

    @Autowired
    private UnitDomainService domainService;

    @EventListener
    public void handleCustomEvent(UpdateCourseEvent event) {
        // 删除老数据
        List<Unit> byCourseId = domainService.getByCourseId(event.getCourse().getId());
        byCourseId.stream().map(Unit::getId).distinct().forEach(id -> domainService.delete(id));
        // 新增单元数据
        Optional.ofNullable(event.getCourse().getUnits()).ifPresent(units -> {
            List<Unit> collect = units.stream().map(unit -> domainService.save(unit)).collect(Collectors.toList());
            event.getCourse().setUnits(collect);
        });
    }
}
