package cn.edu.hcnu.program.domain.event.folder.remove;

import cn.edu.hcnu.program.infrastructure.repository.FileRepository;
import cn.edu.hcnu.program.model.po.FilePO;
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
