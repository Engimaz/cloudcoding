package cc.cloudcoding.program.domain.service.program;

import cc.cloudcoding.program.domain.service.relation.ProgramUser;
import cc.cloudcoding.program.domain.event.program.ProgramPublisher;
import cc.cloudcoding.program.infrastructure.repository.ProgramRepository;
import cc.cloudcoding.program.model.po.ProgramPO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Setter
@Getter
@RequiredArgsConstructor
@Scope("prototype")
public class Program {

    private String name;
    private String id;
    private String description;
    private String language;
    private String sdk;
    private String avatar;
    private List<ProgramUser> relations;

    private final ProgramRepository programRepository;

    private final ProgramPublisher programPublisher;


    public void save() {
        ProgramPO programPO = new ProgramPO();
        programPO.setId(Long.valueOf(this.getId()));
        programPO.setAvatar(this.getAvatar());
        programPO.setName(this.getName());
        programPO.setDescription(this.getDescription());
        programPO.setLanguage(this.getLanguage());
        programPO.setSdk(this.getSdk());
        boolean save = programRepository.save(programPO);
        if (save) {
            programPublisher.pulishCreateProgram(this);
        }
    }


    public boolean remove(String id) {
        boolean b = programRepository.removeById(id);
        if (b) {
            programPublisher.pulishRemoveProgram(Long.valueOf(id));
        }
        return b;
    }

    public void update() {
        ProgramPO programPO = new ProgramPO();
        programPO.setId(Long.valueOf(this.getId()));
        programPO.setAvatar(this.getAvatar());
        programPO.setName(this.getName());
        programPO.setDescription(this.getDescription());
        programPO.setLanguage(this.getLanguage());
        programPO.setSdk(this.getSdk());
        boolean update = programRepository.updateById(programPO);
        if (update) {
            programPublisher.pulishUpdateProgram(this);
        }
    }


    public void render() {
        ProgramPO programPO = programRepository.getById(id);
        this.setId(String.valueOf(programPO.getId()));
        this.setName(programPO.getName());
        this.setLanguage(programPO.getLanguage());
        this.setSdk(programPO.getSdk());
        this.setAvatar(programPO.getAvatar());
        this.setDescription(programPO.getDescription());

    }
}
