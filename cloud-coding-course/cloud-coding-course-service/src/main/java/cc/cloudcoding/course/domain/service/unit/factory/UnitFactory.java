package cc.cloudcoding.course.domain.service.unit.factory;

import cc.cloudcoding.course.domain.service.node.NodeDomainService;
import cc.cloudcoding.course.domain.service.unit.Unit;
import cc.cloudcoding.course.model.po.UnitPO;
import cc.cloudcoding.id.domain.service.IDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UnitFactory {
    @Autowired
    @Qualifier("snowflake")
    private IDGenerator idGenerator;

    @Autowired
    private NodeDomainService nodeDomainService;


    public Unit createUnit(UnitPO p) {
        Unit unit = new Unit();
        unit.setId(String.valueOf(p.getId()));
        unit.setName(p.getName());
        unit.setNodes(nodeDomainService.listByUnitId(String.valueOf(p.getId())));
        unit.setOrder(p.getOrder());
        unit.setCourseId(String.valueOf(p.getCourseId()));
        return unit;
    }

    public UnitPO createUnitPO(Unit unit) {
        UnitPO p = new UnitPO();
        if (unit.getId() == null) {
            p.setId(Long.valueOf(idGenerator.nextID()));
        } else {
            p.setId(Long.valueOf(unit.getId()));
        }
        p.setName(unit.getName());
        p.setOrder(unit.getOrder());
        p.setCourseId(Long.valueOf(unit.getCourseId()));
        return p;
    }

    public List<Unit> createUnit(List<UnitPO> list) {
        return list.stream().map(this::createUnit).collect(Collectors.toList());
    }
}
