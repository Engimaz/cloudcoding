package cc.cloudcoding.comment.domain.comment;

import cc.cloudcoding.comment.domain.comment.remove.RemoveCommentEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 13:41
 */
@Component
public class CommentPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public CommentPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishRemoveCommentEvent(String removeId) {
        eventPublisher.publishEvent(new RemoveCommentEvent(this, removeId));
    }
}
