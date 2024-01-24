package cc.cloudcoding.program.domain.event.folder.create;

import cc.cloudcoding.program.domain.service.file.File;
import cc.cloudcoding.program.domain.service.folder.Folder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class CreateFolderListener {


    @EventListener
    public void handleCustomEvent(CreateFolderEvent event) {
        if (event.getFolder() == null) {
            return;
        }
        // 保存他的子文件夹
        event.getFolder().getFolders().forEach(Folder::save);

        event.getFolder().getFiles().forEach(File::save);

    }
}
