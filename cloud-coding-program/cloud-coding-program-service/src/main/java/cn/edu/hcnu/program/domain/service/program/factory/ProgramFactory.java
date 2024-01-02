package cn.edu.hcnu.program.domain.service.program.factory;

import cn.edu.hcnu.id.domain.service.IDGenerator;
import cn.edu.hcnu.program.domain.service.program.Program;
import cn.edu.hcnu.program.infrastructure.repository.ProgramUserRepository;
import cn.edu.hcnu.program.model.po.ProgramPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProgramFactory {

    @Autowired
    @Qualifier("snowflake")
    private IDGenerator idGenerator;
    @Autowired
    private ProgramUserRepository programUserRepository;

    public Program createProgram(ProgramPO programPO) {
        Program program = new Program();
        program.setId(String.valueOf(programPO.getId()));
        program.setName(programPO.getName());
        program.setLanguage(programPO.getLanguage());
        program.setSdk(programPO.getSdk());
        program.setDescription(programPO.getDescription());
        return program;
    }

    public List<Program> createProgram(List<ProgramPO> programPOs){
        return programPOs.stream().map(this::createProgram).collect(Collectors.toList());
    }

    public ProgramPO createProgramPO(Program program) {
        ProgramPO programPO = new ProgramPO();
        if (program.getId() == null) {
            programPO.setId(Long.valueOf(idGenerator.nextID()));
        } else {
            programPO.setId(Long.valueOf(program.getId()));
        }
        programPO.setName(program.getName());
        programPO.setDescription(program.getDescription());
        programPO.setLanguage(program.getLanguage());
        programPO.setSdk(program.getSdk());
        return programPO;
    }
}
