package cn.edu.hcnu.comment.domain.comment.remove;

import org.springframework.context.ApplicationEvent;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 13:46
 */
public class RemoveCommentEvent extends ApplicationEvent {

    private String removeId;

    public RemoveCommentEvent(Object source, String removeId) {
        super(source);
        this.removeId = removeId;
    }

    public String getRemoveId() {
        return this.removeId;
    }
}

