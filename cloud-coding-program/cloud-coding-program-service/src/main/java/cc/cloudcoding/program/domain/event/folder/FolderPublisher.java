package cc.cloudcoding.program.domain.event.folder;

import cc.cloudcoding.program.domain.event.folder.remove.RemoveFolderEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class FolderPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public FolderPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void pushliRemoveEvent(String folderId) {
        RemoveFolderEvent removeFolderEvent = new RemoveFolderEvent(this, Long.valueOf(folderId));
        eventPublisher.publishEvent(removeFolderEvent);
    }
}
