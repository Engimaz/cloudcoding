package cn.edu.hcnu.manager.domain.event.organization.update;

import cn.edu.hcnu.manager.domain.service.organization.Organization;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/13 15:27
 */
@Getter
public class UpdateOrganizationEvent extends ApplicationEvent {
    private Organization organization;

    public UpdateOrganizationEvent(Object source, Organization organization) {
        super(source);
        this.organization = organization;
    }
}
