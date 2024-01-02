package cn.edu.hcnu.program.domain.service.relation.factory;

import cn.edu.hcnu.id.domain.service.IDGenerator;
import cn.edu.hcnu.program.domain.service.relation.ProgramUser;
import cn.edu.hcnu.program.model.po.ProgramUserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProgramUserFactory {

    @Autowired
    @Qualifier("snowflake")
    private IDGenerator idGenerator;

    public ProgramUserPO createProgramUserPO(ProgramUser programUser) {
        ProgramUserPO programUserPO = new ProgramUserPO();
        if (programUser.getId() == null) {
            programUserPO.setId(Long.valueOf(idGenerator.nextID()));
        } else {
            programUserPO.setId(programUser.getId());
        }
        programUserPO.setProgramId(Long.valueOf(programUser.getProgramId()));
        programUserPO.setUserId(Long.valueOf(programUser.getUserId()));
        programUserPO.setRole(programUser.getRole());
        return programUserPO;
    }

    public List<ProgramUserPO> createProgramUserPO(List<ProgramUser> programUsers) {
        return programUsers.stream().map(this::createProgramUserPO).collect(Collectors.toList());
    }

    public ProgramUser createProgramUser(ProgramUserPO programUserPO) {
        ProgramUser programUser = new ProgramUser();
        programUser.setId(programUserPO.getId());
        programUser.setUserId(String.valueOf(programUserPO.getUserId()));
        programUser.setProgramId(String.valueOf(programUserPO.getProgramId()));
        programUser.setRole(programUserPO.getRole());
        return programUser;
    }

    public List<ProgramUser> createProgramUser(List<ProgramUserPO> list) {
        return list.stream().map(this::createProgramUser).collect(Collectors.toList());
    }

}
