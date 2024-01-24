package cc.cloudcoding.course.application.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.course.domain.service.course.Course;
import cc.cloudcoding.course.model.command.UpdateCourseCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateCourseCommandToCourseMapping extends IMapping<UpdateCourseCommand, Course> {
}
