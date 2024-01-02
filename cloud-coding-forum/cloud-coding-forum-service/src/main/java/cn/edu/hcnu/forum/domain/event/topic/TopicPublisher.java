package cn.edu.hcnu.forum.domain.event.topic;


import cn.edu.hcnu.forum.domain.event.topic.add.AddTopicEvent;
import cn.edu.hcnu.forum.domain.event.topic.remove.RemoveTopicEvent;
import cn.edu.hcnu.forum.domain.event.topic.update.UpdateTopicEvent;
import cn.edu.hcnu.forum.domain.service.Topic;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 13:41
 */
@Component
public class TopicPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public TopicPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishRemoveTopicEvent(String removeTopicId, boolean deleteArticle) {
        eventPublisher.publishEvent(new RemoveTopicEvent(this, removeTopicId,deleteArticle));
    }

    public void publishUpdateTopicEvent(Topic Topic) {
        eventPublisher.publishEvent(new UpdateTopicEvent(this, Topic));
    }
    public void publishAddTopicEvent(Topic Topic) {
        eventPublisher.publishEvent(new AddTopicEvent(this, Topic));
    }
}
