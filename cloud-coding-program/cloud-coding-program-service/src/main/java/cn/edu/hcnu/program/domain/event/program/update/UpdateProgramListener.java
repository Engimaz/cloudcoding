package cn.edu.hcnu.program.domain.event.program.update;

import cn.edu.hcnu.program.domain.service.relation.ProgramUser;
import cn.edu.hcnu.program.domain.service.relation.ProgramUserDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UpdateProgramListener {


    @Autowired
    private ProgramUserDomainService programUserDomainService;


    @EventListener
    public void handleCustomEvent(UpdateProgramEvent event) {


        // 移除旧关系
        programUserDomainService.removeProgramAllUser(Long.valueOf(event.getProgram().getId()));
        // 新增关系
        List<ProgramUser> relations = event.getProgram().getRelations();
        programUserDomainService.batchSave(relations);


    }
}
