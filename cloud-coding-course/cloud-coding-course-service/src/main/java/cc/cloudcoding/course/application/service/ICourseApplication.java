package cc.cloudcoding.course.application.service;

import cc.cloudcoding.base.model.CommonQuery;
import cc.cloudcoding.base.model.PageDTO;
import cc.cloudcoding.course.model.command.AddCourseCommand;
import cc.cloudcoding.course.model.command.UpdateCourseCommand;
import cc.cloudcoding.course.model.dto.CourseDTO;


public interface ICourseApplication {
    PageDTO<CourseDTO, CommonQuery> list(CommonQuery query);

    boolean delete(String id);

    CourseDTO getById(String id);

    CourseDTO save(AddCourseCommand addCourseCommand);



    CourseDTO update(UpdateCourseCommand command);
}
