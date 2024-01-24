package cc.cloudcoding.program.domain.event.folder.remove;

import cc.cloudcoding.program.infrastructure.repository.FileRepository;
import cc.cloudcoding.program.model.po.FilePO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
    private FileRepository fileRepository;

    @EventListener
    public void handleCustomEvent(RemoveFolderEvent event) {
        fileRepository.remove(new LambdaQueryWrapper<FilePO>().eq(FilePO::getFolderId, event.getFolderId()));
    }
}
