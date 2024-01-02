package cn.edu.hcnu.program.domain.event.program.remove;

import org.springframework.context.ApplicationEvent;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 13:46
 */
public class RemoveProgramEvent extends ApplicationEvent {

    private Long programId;

    public RemoveProgramEvent(Object source, Long programId) {
        super(source);
        this.programId = programId;
    }

    public Long getProgramId() {
        return this.programId;
    }
}

