package cn.edu.hcnu.dictionary.domain.group;

import cn.edu.hcnu.dictionary.domain.group.create.CreateGroupEvent;
import cn.edu.hcnu.dictionary.domain.group.remove.RemoveGroupEvent;
import cn.edu.hcnu.dictionary.domain.group.update.UpdateGroupEvent;
import cn.edu.hcnu.dictionary.domain.service.group.Group;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/26 14:04
 */

@Service
public class GroupPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public GroupPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishRemoveGroupEvent(Long removeGroupId) {
        RemoveGroupEvent removeGroupEvent = new RemoveGroupEvent(this, removeGroupId);
        eventPublisher.publishEvent(removeGroupEvent);
    }

    public void publishUpdateGroupEvent(Group group) {
        UpdateGroupEvent updateGroupEvent = new UpdateGroupEvent(this, group);
        eventPublisher.publishEvent(updateGroupEvent);
    }

    public void publishCreateGroupEvent(Group group) {
        CreateGroupEvent createGroupEvent = new CreateGroupEvent(this, group);
        eventPublisher.publishEvent(createGroupEvent);
    }

}
