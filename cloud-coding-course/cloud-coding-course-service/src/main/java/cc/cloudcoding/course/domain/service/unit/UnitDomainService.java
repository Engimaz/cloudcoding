package cc.cloudcoding.course.domain.service.unit;

import cc.cloudcoding.course.domain.event.unit.UnitPublisher;
import cc.cloudcoding.course.domain.service.unit.factory.UnitFactory;
import cc.cloudcoding.course.infrastructure.repository.UnitRepository;
import cc.cloudcoding.course.model.po.UnitPO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UnitDomainService {

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private UnitFactory unitFactory;

    @Autowired
    private UnitPublisher unitPublisher;

    public List<Unit> getByCourseId(String courseId) {
        List<UnitPO> list = unitRepository.list(new LambdaQueryWrapper<UnitPO>().eq(UnitPO::getCourseId, courseId));
        return unitFactory.createUnit(list);
    }

    public Unit getById(String id) {
        UnitPO unitPO = unitRepository.getById(id);
        return unitFactory.createUnit(unitPO);
    }

    public boolean delete(String id) {
        return unitRepository.removeById(id);
    }

    public Unit save(Unit unit) {
        UnitPO unitPO = unitFactory.createUnitPO(unit);
        if (unitRepository.save(unitPO)) {
            unit.setId(String.valueOf(unitPO.getId()));
            unitPublisher.publishAddUnitEvent(unit);
            return unit;
        }
        return null;
    }


}
