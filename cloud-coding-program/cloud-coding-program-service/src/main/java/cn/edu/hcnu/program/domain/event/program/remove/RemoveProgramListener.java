package cn.edu.hcnu.program.domain.event.program.remove;

import cn.edu.hcnu.program.domain.service.folder.FolderDomainService;
import cn.edu.hcnu.program.infrastructure.repository.ProgramUserRepository;
import cn.edu.hcnu.program.model.po.ProgramUserPO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
    private FolderDomainService folderDomainService;

    @Autowired
    private ProgramUserRepository programUserRepository;

    @EventListener
    public void handleCustomEvent(RemoveProgramEvent event) {
        // 删除所有关系
        boolean b = programUserRepository.remove(new LambdaQueryWrapper<ProgramUserPO>().eq(ProgramUserPO::getProgramId, event.getProgramId()));
        // 删除所有文件夹
        boolean b1 = folderDomainService.removeProgramFolderByProgramId(event.getProgramId());

        if (b && b1) {
            log.info("成功删除 {} 项目的成员和文件夹及文件", event.getProgramId());
        }
    }
}
