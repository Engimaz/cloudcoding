package cc.cloudcoding.program.domain.event.folder.update;

import cc.cloudcoding.program.domain.service.folder.Folder;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class UpdateFolderEvent extends ApplicationEvent {

    private final Folder folder;

    public UpdateFolderEvent(Object source, Folder folder) {
        super(source);
        this.folder = folder;
    }

}

