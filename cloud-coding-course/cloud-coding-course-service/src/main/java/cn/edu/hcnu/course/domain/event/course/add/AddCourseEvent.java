package cn.edu.hcnu.course.domain.event.course.add;

import cn.edu.hcnu.course.domain.event.course.CoursePublisher;
import cn.edu.hcnu.course.domain.service.course.Course;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AddCourseEvent extends ApplicationEvent {

    private Course course;

    public AddCourseEvent(CoursePublisher source, Course course) {
        super(source);
        this.course = course;
    }
}
