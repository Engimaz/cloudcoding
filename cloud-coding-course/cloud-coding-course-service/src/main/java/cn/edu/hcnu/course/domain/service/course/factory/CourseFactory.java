package cn.edu.hcnu.course.domain.service.course.factory;

import cn.edu.hcnu.course.domain.service.course.Course;
import cn.edu.hcnu.course.domain.service.unit.UnitDomainService;
import cn.edu.hcnu.course.model.po.CoursePO;
import cn.edu.hcnu.dictionary.rpc.DictionaryService;
import cn.edu.hcnu.id.domain.service.IDGenerator;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CourseFactory {


    @Autowired
    @Qualifier(value = "snowflake")
    private IDGenerator idGenerator;

    @Autowired
    private UnitDomainService unitDomainService;

    public Course createCourse(CoursePO c) {
        Course course = new Course();
        course.setId(String.valueOf(c.getId()));
        course.setName(c.getName());
        course.setAvatar(c.getAvatar());
        course.setDescription(c.getDescription());
        course.setUnits(unitDomainService.getByCourseId(String.valueOf(c.getId())));
        return course;
    }

    public CoursePO createCoursePO(Course c) {
        CoursePO course = new CoursePO();
        if (c.getId() == null) {
            course.setId(Long.valueOf(idGenerator.nextID()));
        } else {
            course.setId(Long.valueOf(c.getId()));
        }
        course.setName(c.getName());
        course.setAvatar(c.getAvatar());
        course.setDescription(c.getDescription());
        return course;
    }

}

