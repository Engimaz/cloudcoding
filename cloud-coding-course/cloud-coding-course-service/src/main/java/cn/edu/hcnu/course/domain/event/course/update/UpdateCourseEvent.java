package cn.edu.hcnu.course.domain.event.course.update;

import cn.edu.hcnu.course.domain.service.course.Course;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 13:46
 */

@Getter
public class UpdateCourseEvent extends ApplicationEvent {

    private Course course;

    public UpdateCourseEvent(Object source, Course course) {
        super(source);
        this.course = course;
    }

}

