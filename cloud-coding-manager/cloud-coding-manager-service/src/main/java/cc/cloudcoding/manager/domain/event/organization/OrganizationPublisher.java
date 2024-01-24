package cc.cloudcoding.manager.domain.event.organization;

import cc.cloudcoding.manager.domain.event.organization.add.AddOrganizationEvent;
import cc.cloudcoding.manager.domain.event.organization.remove.RemoveOrganizationEvent;
import cc.cloudcoding.manager.domain.event.organization.update.UpdateOrganizationEvent;
import cc.cloudcoding.manager.domain.service.organization.Organization;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/13 14:50
 */
@Component
public class OrganizationPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public OrganizationPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishAddOrganizationEvent(Organization organization) {
        eventPublisher.publishEvent(new AddOrganizationEvent(this, organization));
    }

    public void publishRemoveOrganizationEvent(Long id) {
        eventPublisher.publishEvent(new RemoveOrganizationEvent(this, id));
    }

    public void publishUpdateOrganizationEvent(Organization organization) {
        eventPublisher.publishEvent(new UpdateOrganizationEvent(this, organization));
    }
}
