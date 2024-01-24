package cc.cloudcoding.forum.domain.event.topic.update;

import cc.cloudcoding.forum.domain.service.Topic;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UpdateTopicEvent extends ApplicationEvent {

    private Topic topic;

    public UpdateTopicEvent(Object source, Topic topic) {
        super(source);
        this.topic = topic;
    }

}

