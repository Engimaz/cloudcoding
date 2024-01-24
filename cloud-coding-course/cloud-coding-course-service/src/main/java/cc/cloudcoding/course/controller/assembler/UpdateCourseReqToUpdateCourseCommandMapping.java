package cc.cloudcoding.course.controller.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.course.model.command.UpdateCourseCommand;
import cc.cloudcoding.course.model.req.UpdateCourseReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateCourseReqToUpdateCourseCommandMapping extends IMapping<UpdateCourseReq, UpdateCourseCommand> {
}
