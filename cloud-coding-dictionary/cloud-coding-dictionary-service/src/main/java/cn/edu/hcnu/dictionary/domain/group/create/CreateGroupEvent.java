package cn.edu.hcnu.dictionary.domain.group.create;

import cn.edu.hcnu.dictionary.domain.service.group.Group;
import org.springframework.context.ApplicationEvent;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/26 14:02
 */
public class CreateGroupEvent extends ApplicationEvent {

    private Group group;

    public CreateGroupEvent(Object source, Group group) {
        super(source);
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }
}
