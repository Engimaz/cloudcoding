package cn.edu.hcnu.program.domain.event.program.create;

import cn.edu.hcnu.program.domain.service.program.Program;
import org.springframework.context.ApplicationEvent;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 13:46
 */
public class CreateProgramEvent extends ApplicationEvent {

    private Program program;

    public CreateProgramEvent(Object source, Program program) {
        super(source);
        this.program = program;
    }

    public Program getProgram() {
        return this.program;
    }
}

