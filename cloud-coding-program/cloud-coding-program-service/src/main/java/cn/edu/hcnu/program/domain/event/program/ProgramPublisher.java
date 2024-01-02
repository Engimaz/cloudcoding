package cn.edu.hcnu.program.domain.event.program;

import cn.edu.hcnu.program.domain.event.program.create.CreateProgramEvent;
import cn.edu.hcnu.program.domain.event.program.remove.RemoveProgramEvent;
import cn.edu.hcnu.program.domain.event.program.update.UpdateProgramEvent;
import cn.edu.hcnu.program.domain.service.program.Program;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ProgramPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public ProgramPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void pulishRemoveProgram(Long id) {
        RemoveProgramEvent removeProgramEvent = new RemoveProgramEvent(this, id);
        eventPublisher.publishEvent(removeProgramEvent);
    }

    public void pulishCreateProgram(Program program) {
        CreateProgramEvent createProgramEvent = new CreateProgramEvent(this, program);
        eventPublisher.publishEvent(createProgramEvent);
    }

    public void pulishUpdateProgram(Program updateProgram) {
        UpdateProgramEvent updateProgramEvent = new UpdateProgramEvent(this, updateProgram);
        eventPublisher.publishEvent(updateProgramEvent);
    }
}
