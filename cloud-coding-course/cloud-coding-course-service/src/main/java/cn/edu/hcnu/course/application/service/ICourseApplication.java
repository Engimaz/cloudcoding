package cn.edu.hcnu.course.application.service;

import cn.edu.hcnu.base.model.CommonQuery;
import cn.edu.hcnu.base.model.PageDTO;
import cn.edu.hcnu.course.model.command.AddCourseCommand;
import cn.edu.hcnu.course.model.command.UpdateCourseCommand;
import cn.edu.hcnu.course.model.dto.CourseDTO;


public interface ICourseApplication {
    PageDTO<CourseDTO, CommonQuery> list(CommonQuery query);

    boolean delete(String id);

    CourseDTO getById(String id);

    CourseDTO save(AddCourseCommand addCourseCommand);



    CourseDTO update(UpdateCourseCommand command);
}
