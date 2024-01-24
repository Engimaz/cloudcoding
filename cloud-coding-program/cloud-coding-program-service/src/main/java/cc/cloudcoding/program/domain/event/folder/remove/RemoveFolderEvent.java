package cc.cloudcoding.program.domain.event.folder.remove;

import cc.cloudcoding.program.domain.service.folder.Folder;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 13:46
 */
@Getter
public class RemoveFolderEvent extends ApplicationEvent {

    private final Folder folder;

    public RemoveFolderEvent(Object source, Folder folder) {
        super(source);
        this.folder = folder;
    }

}

