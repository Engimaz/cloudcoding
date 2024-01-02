package cn.edu.hcnu.course.application.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.course.domain.service.course.Course;
import cn.edu.hcnu.course.model.command.AddCourseCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddCourseCommandToCourseMapping extends IMapping<AddCourseCommand, Course> {
}
