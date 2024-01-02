package cn.edu.hcnu.program.domain.event.program.create;

import cn.edu.hcnu.program.domain.service.folder.Folder;
import cn.edu.hcnu.program.domain.service.folder.FolderDomainService;
import cn.edu.hcnu.program.domain.service.folder.factory.FolderFactory;
import cn.edu.hcnu.program.domain.service.relation.ProgramUserDomainService;
import cn.edu.hcnu.program.model.po.FolderPO;
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
public class CreateProgramListener {


    @Autowired
    private ProgramUserDomainService programUserDomainService;

    @Autowired
    private FolderDomainService folderDomainService;

    @Autowired
    private FolderFactory folderFactory;


    @EventListener
    public void handleCustomEvent(CreateProgramEvent event) {
        if (event.getProgram().getRelations() == null) {
            return;
        }
        // 每个关系添加项目id
        event.getProgram().getRelations().forEach(f -> f.setProgramId(event.getProgram().getId()));
        // 新增关系
        long count = event.getProgram().getRelations().stream().filter(f -> programUserDomainService.save(f) != null).map(f -> 1).count();
        if (event.getProgram().getRelations().size() == count) {
            log.info("{} 项目关系添加成功 ", event.getProgram().getName());
        }
        // 为项目新建文件夹
        Folder programFolder = new Folder();
        FolderPO folderPo = folderFactory.createFolderPo(new Folder());
        folderPo.setName(event.getProgram().getName());
        folderPo.setParentId(folderPo.getId());
        folderPo.setProgramId(Long.valueOf(event.getProgram().getId()));
        Folder newProgramFolder = folderDomainService.save(folderFactory.createFolder(folderPo));
        if (newProgramFolder != null) {
            log.info("{} 项目创建文件夹成功 ", event.getProgram().getName());
        }
        // 根据模板添加文件
    }
}
