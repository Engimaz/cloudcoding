package cn.edu.hcnu.program.domain.event.program.remove;

import cn.edu.hcnu.program.domain.service.folder.FolderDomainService;
import cn.edu.hcnu.program.domain.service.relation.ProgramUserDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 13:46
 */
@Service
@Slf4j
public class RemoveProgramListener {


    @Autowired
    private ProgramUserDomainService programUserDomainService;

    @Autowired
    private FolderDomainService folderDomainService;


    @EventListener
    public void handleCustomEvent(RemoveProgramEvent event) {
        // 删除所有关系
        boolean b = programUserDomainService.removeProgramAllUser(Long.valueOf(event.getProgramId()));
        // 删除所有文件夹
        boolean b1 = folderDomainService.removeProgramFolderByProgramId(event.getProgramId());

        if (b && b1) {
            log.info("成功删除 {} 项目的成员和文件夹及文件", event.getProgramId());
        }
    }
}
