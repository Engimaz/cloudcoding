package cc.cloudcoding.course.application.service.impl;

import cc.cloudcoding.base.model.CommonQuery;
import cc.cloudcoding.base.model.PageDTO;
import cc.cloudcoding.course.application.assembler.AddCourseCommandToCourseMapping;
import cc.cloudcoding.course.application.assembler.CourseToCourseDTOMapping;
import cc.cloudcoding.course.application.assembler.UpdateCourseCommandToCourseMapping;
import cc.cloudcoding.course.application.service.ICourseApplication;
import cc.cloudcoding.course.domain.service.course.Course;
import cc.cloudcoding.course.domain.service.course.CourseDomainService;
import cc.cloudcoding.course.model.command.AddCourseCommand;
import cc.cloudcoding.course.model.command.UpdateCourseCommand;
import cc.cloudcoding.course.model.dto.CourseDTO;
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
