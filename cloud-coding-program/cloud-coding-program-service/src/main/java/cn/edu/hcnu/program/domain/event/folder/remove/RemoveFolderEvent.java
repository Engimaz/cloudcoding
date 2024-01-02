package cn.edu.hcnu.program.domain.event.folder.remove;

import org.springframework.context.ApplicationEvent;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 13:46
 */
public class RemoveFolderEvent extends ApplicationEvent {

    private Long folderId;

    public RemoveFolderEvent(Object source, Long folderId) {
        super(source);
        this.folderId = folderId;
    }

    public Long getFolderId() {
        return this.folderId;
    }
}

