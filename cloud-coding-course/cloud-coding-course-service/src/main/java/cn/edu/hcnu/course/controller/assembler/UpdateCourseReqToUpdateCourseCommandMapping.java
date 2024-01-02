package cn.edu.hcnu.course.controller.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.course.model.command.UpdateCourseCommand;
import cn.edu.hcnu.course.model.req.UpdateCourseReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateCourseReqToUpdateCourseCommandMapping extends IMapping<UpdateCourseReq, UpdateCourseCommand> {
}
