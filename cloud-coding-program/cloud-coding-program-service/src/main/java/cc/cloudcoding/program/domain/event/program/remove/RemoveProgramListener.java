package cc.cloudcoding.program.domain.event.program.remove;

import cc.cloudcoding.program.domain.service.folder.Folder;

import cc.cloudcoding.program.infrastructure.repository.FolderRepository;
import cc.cloudcoding.program.infrastructure.repository.ProgramUserRepository;
import cc.cloudcoding.program.model.po.FolderPO;
import cc.cloudcoding.program.model.po.ProgramUserPO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 13:46
 */
@Service
@Slf4j
public class RemoveProgramListener {




    @Autowired
    private ProgramUserRepository programUserRepository;
    @Autowired
    private FolderRepository folderRepository;
    @Autowired
    private ApplicationContext applicationContext;

    @EventListener
    public void handleCustomEvent(RemoveProgramEvent event) {
        // 删除所有关系
        boolean b = programUserRepository.remove(new LambdaQueryWrapper<ProgramUserPO>().eq(ProgramUserPO::getProgramId, event.getProgramId()));

        // 属于这个项目的所有文件夹
        List<FolderPO> list = folderRepository.list(new LambdaQueryWrapper<FolderPO>().eq(FolderPO::getProgramId, event.getProgramId()));
        // 删除文件夹
        long count = list.stream().map(FolderPO::getId).distinct().map(String::valueOf).filter(f -> {
            Folder bean = applicationContext.getBean(Folder.class);
            bean.setId(f);
            bean.remove();
            return true;
        }).map(res -> 1).count();
        boolean b1 = count == list.size();
        if (b && b1) {
            log.info("成功删除 {} 项目的成员和文件夹及文件", event.getProgramId());
        }
    }
}
