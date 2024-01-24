package cc.cloudcoding.forum.domain.event.topic.remove;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 13:46
 */

@Getter
public class RemoveTopicEvent extends ApplicationEvent {

    private String topicId;

    // 是否删除文章
    private boolean deleteArticle;

    public RemoveTopicEvent(Object source, String topicId, boolean deleteArticle) {
        super(source);
        this.deleteArticle = deleteArticle;
        this.topicId = topicId;
    }

}

