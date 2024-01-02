package cn.edu.hcnu.course.domain.event.unit.remove;

import org.springframework.context.ApplicationEvent;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 13:46
 */
public class RemoveUnitEvent extends ApplicationEvent {

    private String removeId;

    public RemoveUnitEvent(Object source, String removeId) {
        super(source);
        this.removeId = removeId;
    }

    public String getRemoveId() {
        return this.removeId;
    }
}

