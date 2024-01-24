package cc.cloudcoding.course.controller.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.course.model.dto.CourseDTO;
import cc.cloudcoding.course.model.res.CourseRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CourseDTOToCourseResMapping implements IMapping<CourseDTO, CourseRes> {
    @Autowired
    private UnitDTOToUnitResMapping unitDTOToUnitResMapping;
    @Override
    public CourseRes sourceToTarget(CourseDTO var1) {
        CourseRes courseRes = new CourseRes();
        courseRes.setId(var1.getId());
        courseRes.setName(var1.getName());
        courseRes.setDescription(var1.getDescription());
        courseRes.setAvatar(var1.getAvatar());
        courseRes.setUnits(unitDTOToUnitResMapping.sourceToTarget(var1.getUnits()));
        return courseRes;
    }

    @Override
    public CourseDTO targetToSource(CourseRes var1) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(var1.getId());
        courseDTO.setName(var1.getName());
        courseDTO.setDescription(var1.getDescription());
        courseDTO.setAvatar(var1.getAvatar());
        courseDTO.setUnits(unitDTOToUnitResMapping.targetToSource(var1.getUnits()));
        return courseDTO;
    }

    @Override
    public List<CourseRes> sourceToTarget(List<CourseDTO> var1) {
        return this.sourceToTarget(var1.stream());
    }

    @Override
    public List<CourseDTO> targetToSource(List<CourseRes> var1) {
        return this.targetToSource(var1.stream());
    }

    @Override
    public List<CourseRes> sourceToTarget(Stream<CourseDTO> stream) {
        return stream.map(this::sourceToTarget).collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> targetToSource(Stream<CourseRes> stream) {
        return stream.map(this::targetToSource).collect(Collectors.toList());
    }
}
