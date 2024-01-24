package cc.cloudcoding.manager.domain.event.url;

import cc.cloudcoding.manager.domain.event.url.remove.RemoveUrlEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 13:41
 */
@Component
public class UrlPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public UrlPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishRemoveUrlEvent(Long urlId) {
        eventPublisher.publishEvent(new RemoveUrlEvent(this, urlId));
    }
}
