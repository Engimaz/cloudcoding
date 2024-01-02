package cn.edu.hcnu.program.domain.service.program;

import cn.edu.hcnu.program.domain.event.program.ProgramPublisher;
import cn.edu.hcnu.program.domain.service.program.factory.ProgramFactory;
import cn.edu.hcnu.program.infrastructure.repository.ProgramRepository;
import cn.edu.hcnu.program.model.po.ProgramPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramDomainService {


    @Autowired
    private ProgramFactory programFactory;
    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private ProgramPublisher programPublisher;

    public Program save(Program program) {
        ProgramPO programPO = programFactory.createProgramPO(program);
        boolean save = programRepository.save(programPO);
        if (save) {
            program.setId(String.valueOf(programPO.getId()));
            programPublisher.pulishCreateProgram(program);
            return program;
        }
        return null;
    }


    public boolean remove(String id) {
        boolean b = programRepository.removeById(id);
        if (b) {
            programPublisher.pulishRemoveProgram(Long.valueOf(id));
        }
        return b;
    }

    public Program update(Program program) {
        ProgramPO programPO = programFactory.createProgramPO(program);
        boolean update = programRepository.updateById(programPO);
        if(update){
            Program updateProgram = programFactory.createProgram(programPO);
            programPublisher.pulishUpdateProgram(updateProgram);
            return updateProgram;
        }
        return null;
    }

    public List<Program> queryProgramByProgramIds(List<String> programIds) {
        return programFactory.createProgram(programRepository.listByIds(programIds));
    }
    /**
     * @description: 查询项目通过id
     * @param id
            * @return: void
            * @author: Administrator
            * @time: 2023/10/15 2:03
     */

    public Program queryById(String id) {
        return  programFactory.createProgram(programRepository.getById(id));
    }
}
