package cc.cloudcoding.program.domain.event.program.update;

import cc.cloudcoding.program.domain.service.program.Program;
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

