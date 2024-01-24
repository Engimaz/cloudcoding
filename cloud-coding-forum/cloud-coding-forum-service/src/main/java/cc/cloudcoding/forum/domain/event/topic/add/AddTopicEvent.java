package cc.cloudcoding.forum.domain.event.topic.add;

import cn.edu.hcnu.forum.domain.service.Topic;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AddTopicEvent extends ApplicationEvent {

    private Topic topic;

    public AddTopicEvent(Object source, Topic topic) {
        super(source);
        this.topic = topic;
    }

}
