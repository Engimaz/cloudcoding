package cn.edu.hcnu.course.controller.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.course.model.command.UnitCommand;
import cn.edu.hcnu.course.model.req.NodeReq;
import cn.edu.hcnu.course.model.req.UnitReq;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class UnitReqToUnitCommandMapping implements IMapping<UnitReq, UnitCommand> {

    @Autowired
    private NodeReqToNodeCommandMapping nodeReqToNodeCommandMapping;
    @Override
    public UnitCommand sourceToTarget(UnitReq var1) {
        UnitCommand unitCommand = new UnitCommand();
        unitCommand.setName(var1.getName());
        unitCommand.setOrder(var1.getOrder());
        unitCommand.setNodes(nodeReqToNodeCommandMapping.sourceToTarget(var1.getNodes()));
        return unitCommand;
    }

    @Override
    public UnitReq targetToSource(UnitCommand var1) {
        UnitReq unitReq = new UnitReq();
        unitReq.setName(var1.getName());
        unitReq.setOrder(var1.getOrder());
        unitReq.setNodes(nodeReqToNodeCommandMapping.targetToSource(var1.getNodes()));
        return unitReq;
    }

    @Override
    public List<UnitCommand> sourceToTarget(List<UnitReq> var1) {
        return this.sourceToTarget(var1.stream());
    }

    @Override
    public List<UnitReq> targetToSource(List<UnitCommand> var1) {
        return this.targetToSource(var1.stream());
    }

    @Override
    public List<UnitCommand> sourceToTarget(Stream<UnitReq> stream) {
        return stream.map(this::sourceToTarget).collect(Collectors.toList());
    }

    @Override
    public List<UnitReq> targetToSource(Stream<UnitCommand> stream) {
        return stream.map(this::targetToSource).collect(Collectors.toList());
    }
}
