package cn.edu.hcnu.program.domain.event.program.update;

import cn.edu.hcnu.program.domain.service.program.Program;
import org.springframework.context.ApplicationEvent;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 13:46
 */
public class UpdateProgramEvent extends ApplicationEvent {

    private Program program;

    public UpdateProgramEvent(Object source, Program program) {
        super(source);
        this.program = program;
    }

    public Program getProgram() {
        return this.program;
    }
}

