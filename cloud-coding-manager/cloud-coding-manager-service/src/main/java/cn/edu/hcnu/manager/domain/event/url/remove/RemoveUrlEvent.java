package cn.edu.hcnu.manager.domain.event.url.remove;

import org.springframework.context.ApplicationEvent;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 13:46
 */
public class RemoveUrlEvent extends ApplicationEvent {

    private Long urlId;

    public RemoveUrlEvent(Object source, Long urlId) {
        super(source);
        this.urlId = urlId;
    }

    public Long getUrlId() {
        return this.urlId;
    }
}

