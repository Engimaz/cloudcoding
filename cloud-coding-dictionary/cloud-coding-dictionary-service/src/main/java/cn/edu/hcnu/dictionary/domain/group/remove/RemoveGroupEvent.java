package cn.edu.hcnu.dictionary.domain.group.remove;

import org.springframework.context.ApplicationEvent;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/26 14:02
 */
public class RemoveGroupEvent extends ApplicationEvent {

    private Long removeGroupId;

    public RemoveGroupEvent(Object source, Long removeGroupId) {
        super(source);
        this.removeGroupId = removeGroupId;
    }

    public Long getRemoveGroupId() {
        return removeGroupId;
    }
}
