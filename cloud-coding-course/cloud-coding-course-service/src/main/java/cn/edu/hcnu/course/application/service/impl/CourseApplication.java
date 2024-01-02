package cn.edu.hcnu.course.application.service.impl;

import cn.edu.hcnu.base.model.CommonQuery;
import cn.edu.hcnu.base.model.PageDTO;
import cn.edu.hcnu.course.application.assembler.AddCourseCommandToCourseMapping;
import cn.edu.hcnu.course.application.assembler.CourseToCourseDTOMapping;
import cn.edu.hcnu.course.application.assembler.UpdateCourseCommandToCourseMapping;
import cn.edu.hcnu.course.application.service.ICourseApplication;
import cn.edu.hcnu.course.domain.service.course.Course;
import cn.edu.hcnu.course.domain.service.course.CourseDomainService;
import cn.edu.hcnu.course.model.command.AddCourseCommand;
import cn.edu.hcnu.course.model.command.UpdateCourseCommand;
import cn.edu.hcnu.course.model.dto.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseApplication implements ICourseApplication {


    @Autowired
    private CourseDomainService courseDomainService;


    @Autowired
    private AddCourseCommandToCourseMapping addCourseCommandToCourseMapping;

    @Autowired
    private CourseToCourseDTOMapping courseToCourseDTOMapping;

    @Autowired
    private UpdateCourseCommandToCourseMapping updateCourseCommandToCourseMapping;


    @Override
    public PageDTO<CourseDTO, CommonQuery> list(CommonQuery query) {
        List<Course> courses = courseDomainService.query(query.getPage(), query.getSize(), query.getKeyword());
        Long count = courseDomainService.count(query.getPage(), query.getSize(), query.getKeyword());
        return new PageDTO<>(courseToCourseDTOMapping.sourceToTarget(courses), count, query);
    }



    @Override
    public boolean delete(String id) {
        return courseDomainService.delete(id);
    }

    @Override
    public CourseDTO getById(String id) {
        return courseToCourseDTOMapping.sourceToTarget(courseDomainService.getById(id));
    }

    @Override
    public CourseDTO save(AddCourseCommand addCourseCommand) {
        Course course = addCourseCommandToCourseMapping.sourceToTarget(addCourseCommand);
        course = courseDomainService.save(course);
        return courseToCourseDTOMapping.sourceToTarget(course);
    }


    @Override
    public CourseDTO update(UpdateCourseCommand command) {
        Course course = updateCourseCommandToCourseMapping.sourceToTarget(command);
        course = courseDomainService.update(course);
        return courseToCourseDTOMapping.sourceToTarget(course);
    }
}
