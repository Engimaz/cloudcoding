package cn.edu.hcnu.program.domain.service.relation;

import cn.edu.hcnu.program.infrastructure.repository.ProgramUserRepository;
import cn.edu.hcnu.program.model.po.ProgramUserPO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


@Service
@Setter
@Getter
@RequiredArgsConstructor
@Scope("prototype")
public class ProgramUser {

    private Long id;
    private String programId;
    private String userId;
    private String role;
    private final ProgramUserRepository programUserRepository;


    public void save() {
        ProgramUserPO programUserPO = new ProgramUserPO();
        programUserPO.setId(this.getId());
        programUserPO.setProgramId(Long.valueOf(this.getProgramId()));
        programUserPO.setUserId(Long.valueOf(this.getUserId()));
        programUserPO.setRole(this.getRole());
        programUserRepository.save(programUserPO);

    }


    public void update() {
        ProgramUserPO programUserPO = new ProgramUserPO();
        programUserPO.setId(this.getId());
        programUserPO.setProgramId(Long.valueOf(this.getProgramId()));
        programUserPO.setUserId(Long.valueOf(this.getUserId()));
        programUserPO.setRole(this.getRole());
        programUserRepository.updateById(programUserPO);

    }

    public boolean remove() {
        return programUserRepository.removeById(id);
    }

}
