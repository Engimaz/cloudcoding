package cn.edu.hcnu.program.domain.event.program.update;

import cn.edu.hcnu.program.domain.service.program.Program;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class UpdateProgramEvent extends ApplicationEvent {

    private final Program program;

    public UpdateProgramEvent(Object source, Program program) {
        super(source);
        this.program = program;
    }

}

