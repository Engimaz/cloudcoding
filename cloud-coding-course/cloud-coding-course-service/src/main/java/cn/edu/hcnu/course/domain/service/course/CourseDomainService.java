package cn.edu.hcnu.course.domain.service.course;


import cn.edu.hcnu.course.domain.event.course.CoursePublisher;
import cn.edu.hcnu.course.domain.service.course.factory.CourseFactory;
import cn.edu.hcnu.course.infrastructure.repository.CourseRepository;
import cn.edu.hcnu.course.model.po.CoursePO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseDomainService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CoursePublisher coursePublisher;

    @Autowired
    private CourseFactory courseFactory;

    public boolean delete(String id) {
        if (courseRepository.removeById(id)) {
            coursePublisher.publishRemoveCommentEvent(id);
            return true;
        }
        return false;
    }

    public Course save(Course course) {
        CoursePO coursePO = courseFactory.createCoursePO(course);
        if (courseRepository.save(coursePO)) {
            course.setId(String.valueOf(coursePO.getId()));
            coursePublisher.publishAddCourseEvent(course);
            return course;
        }
        return null;
    }

    public Course update(Course course) {
        CoursePO coursePO = courseFactory.createCoursePO(course);
        if (courseRepository.updateById(coursePO)) {
            coursePublisher.publishUpdateCourseEvent(course);
            return course;
        }
        return null;
    }

    public Course getById(String id) {
        return courseFactory.createCourse(courseRepository.getById(id));
    }


    public List<Course> query(Integer page, Integer size, String keyword) {
        return this.pageQuery(page, size, keyword).getRecords().stream().map((f) -> courseFactory.createCourse(f)).collect(Collectors.toList());
    }


    public Long count(Integer page, Integer size, String keyword) {
        return this.pageQuery(page, size, keyword).getTotal();
    }

    private Page<CoursePO> pageQuery(Integer page, Integer size, String keyword) {
        Page<CoursePO> p1 = new Page<>(page, size);
        LambdaQueryWrapper<CoursePO> wrapper = new LambdaQueryWrapper<CoursePO>().like(keyword != "", CoursePO::getName, keyword);
        return courseRepository.getBaseMapper().selectPage(p1, wrapper);
    }
}
