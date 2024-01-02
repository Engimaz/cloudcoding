package cn.edu.hcnu.manager.domain.event.organization.add;

import cn.edu.hcnu.manager.domain.service.organization.Organization;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/13 14:49
 */
@Getter
public class AddOrganizationEvent extends ApplicationEvent {

    private Organization organization;

    public AddOrganizationEvent(Object source, Organization organization) {
        super(source);
        this.organization = organization;
    }
}
