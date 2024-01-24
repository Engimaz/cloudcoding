package cc.cloudcoding.program.domain.event.folder;

import cc.cloudcoding.program.domain.event.folder.create.CreateFolderEvent;
import cc.cloudcoding.program.domain.event.folder.remove.RemoveFolderEvent;
import cc.cloudcoding.program.domain.event.folder.update.UpdateFolderEvent;
import cc.cloudcoding.program.domain.service.folder.Folder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class FolderPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public FolderPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishRemoveEvent(Folder folder) {
        RemoveFolderEvent removeFolderEvent = new RemoveFolderEvent(this, folder);
        eventPublisher.publishEvent(removeFolderEvent);
    }

    public void publishUpdateEvent(Folder folder) {
        UpdateFolderEvent removeFolderEvent = new UpdateFolderEvent(this, folder);
        eventPublisher.publishEvent(removeFolderEvent);
    }

    public void publishCreateEvent(Folder folder) {
        CreateFolderEvent removeFolderEvent = new CreateFolderEvent(this, folder);
        eventPublisher.publishEvent(removeFolderEvent);
    }
}
