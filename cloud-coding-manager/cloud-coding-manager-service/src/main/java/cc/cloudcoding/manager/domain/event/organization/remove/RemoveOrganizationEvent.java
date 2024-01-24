package cc.cloudcoding.manager.domain.event.organization.remove;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/13 15:07
 */

@Getter
public class RemoveOrganizationEvent extends ApplicationEvent {
    private Long id;

    public RemoveOrganizationEvent(Object source, Long id) {
        super(source);
        this.id = id;
    }
}
