package cn.edu.hcnu.program.domain.event.program.update;

import cn.edu.hcnu.program.domain.service.relation.ProgramUser;

import cn.edu.hcnu.program.infrastructure.repository.ProgramUserRepository;
import cn.edu.hcnu.program.model.po.ProgramUserPO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateProgramListener {


    private final ProgramUserRepository programUserRepository;


    @EventListener
    public void handleCustomEvent(UpdateProgramEvent event) {


        // 移除旧关系
        programUserRepository.remove(new LambdaQueryWrapper<ProgramUserPO>().eq(ProgramUserPO::getProgramId, event.getProgram().getId()));
        // 新增关系
        List<ProgramUser> relations = event.getProgram().getRelations();
        List<ProgramUserPO> collect = relations.stream().map(
                programUser -> {
                    ProgramUserPO programUserPO = new ProgramUserPO();
                    programUserPO.setId(programUser.getId());
                    programUserPO.setProgramId(Long.valueOf(programUser.getProgramId()));
                    programUserPO.setUserId(Long.valueOf(programUser.getUserId()));
                    programUserPO.setRole(programUser.getRole());
                    return programUserPO;
                }
        ).collect(Collectors.toList());
        programUserRepository.saveBatch(collect);


    }
}
