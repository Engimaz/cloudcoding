package cn.edu.hcnu.course.domain.event.course;

import cn.edu.hcnu.course.domain.event.course.add.AddCourseEvent;
import cn.edu.hcnu.course.domain.event.course.remove.RemoveCourseEvent;
import cn.edu.hcnu.course.domain.event.course.update.UpdateCourseEvent;
import cn.edu.hcnu.course.domain.service.course.Course;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 13:41
 */
@Component
public class CoursePublisher {
    private final ApplicationEventPublisher eventPublisher;

    public CoursePublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishRemoveCommentEvent(String removeId) {
        eventPublisher.publishEvent(new RemoveCourseEvent(this, removeId));
    }

    public void publishAddCourseEvent(Course course) {
        eventPublisher.publishEvent(new AddCourseEvent(this, course));
    }

    public void publishUpdateCourseEvent(Course course) {
        eventPublisher.publishEvent(new UpdateCourseEvent(this, course));

    }
}
