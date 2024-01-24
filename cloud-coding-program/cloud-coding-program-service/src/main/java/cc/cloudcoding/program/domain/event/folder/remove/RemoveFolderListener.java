package cc.cloudcoding.program.domain.event.folder.remove;

import cc.cloudcoding.program.domain.service.file.File;
import cc.cloudcoding.program.domain.service.folder.Folder;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;


@Service
public class RemoveFolderListener {


    @EventListener
    public void handleCustomEvent(RemoveFolderEvent event) {
        // 删除子文件夹
        event.getFolder().getFolders().forEach(Folder::remove);
        // 删除文件
        event.getFolder().getFiles().forEach(File::remove);
    }
}
