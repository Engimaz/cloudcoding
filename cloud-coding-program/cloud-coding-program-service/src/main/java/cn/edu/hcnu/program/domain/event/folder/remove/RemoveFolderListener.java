package cn.edu.hcnu.program.domain.event.folder.remove;

import cn.edu.hcnu.program.domain.service.file.FileDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 13:46
 */
@Service
public class RemoveFolderListener {


    @Autowired
    private FileDomainService fileDomainService;

    @EventListener
    public void handleCustomEvent(RemoveFolderEvent event) {
        fileDomainService.removeByFolderId(String.valueOf(event.getFolderId()));
    }
}
