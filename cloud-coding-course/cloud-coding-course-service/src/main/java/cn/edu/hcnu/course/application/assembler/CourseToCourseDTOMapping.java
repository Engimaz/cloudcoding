package cn.edu.hcnu.course.application.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.course.domain.service.course.Course;
import cn.edu.hcnu.course.model.dto.CourseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseToCourseDTOMapping extends IMapping<Course, CourseDTO> {
}
