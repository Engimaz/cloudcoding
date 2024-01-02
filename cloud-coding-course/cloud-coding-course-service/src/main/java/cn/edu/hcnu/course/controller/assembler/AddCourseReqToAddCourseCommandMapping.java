package cn.edu.hcnu.course.controller.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.course.model.command.AddCourseCommand;
import cn.edu.hcnu.course.model.req.AddCourseReq;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class AddCourseReqToAddCourseCommandMapping implements IMapping<AddCourseReq, AddCourseCommand> {

    @Autowired
    private UnitReqToUnitCommandMapping unitReqToUnitCommandMapping;
    @Override
    public AddCourseCommand sourceToTarget(AddCourseReq var1) {
        AddCourseCommand addCourseCommand = new AddCourseCommand();
        addCourseCommand.setAvatar(var1.getAvatar());
        addCourseCommand.setName(var1.getName());
        addCourseCommand.setDescription(var1.getDescription());
        addCourseCommand.setUnits(unitReqToUnitCommandMapping.sourceToTarget(var1.getUnits()));
        return addCourseCommand;
    }

    @Override
    public AddCourseReq targetToSource(AddCourseCommand var1) {
        AddCourseReq addCourseReq = new AddCourseReq();
        addCourseReq.setAvatar(var1.getAvatar());
        addCourseReq.setName(var1.getName());
        addCourseReq.setDescription(var1.getDescription());
        addCourseReq.setUnits(unitReqToUnitCommandMapping.targetToSource(var1.getUnits()));
        return addCourseReq;
    }

    @Override
    public List<AddCourseCommand> sourceToTarget(List<AddCourseReq> var1) {
        return this.sourceToTarget(var1.stream());
    }

    @Override
    public List<AddCourseReq> targetToSource(List<AddCourseCommand> var1) {
        return this.targetToSource(var1.stream());
    }

    @Override
    public List<AddCourseCommand> sourceToTarget(Stream<AddCourseReq> stream) {
        return stream.map(this::sourceToTarget).collect(Collectors.toList());
    }

    @Override
    public List<AddCourseReq> targetToSource(Stream<AddCourseCommand> stream) {
        return stream.map(this::targetToSource).collect(Collectors.toList());
    }
}
