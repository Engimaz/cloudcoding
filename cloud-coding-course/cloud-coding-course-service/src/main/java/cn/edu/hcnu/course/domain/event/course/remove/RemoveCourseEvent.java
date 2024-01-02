package cn.edu.hcnu.course.domain.event.course.remove;

import org.springframework.context.ApplicationEvent;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 13:46
 */
public class RemoveCourseEvent extends ApplicationEvent {

    private String removeId;

    public RemoveCourseEvent(Object source, String removeId) {
        super(source);
        this.removeId = removeId;
    }

    public String getRemoveId() {
        return this.removeId;
    }
}

