package cn.edu.hcnu.program.domain.event.program.create;

import cn.edu.hcnu.program.domain.service.folder.Folder;
import cn.edu.hcnu.program.domain.service.relation.ProgramUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class CreateProgramListener {


    private final ApplicationContext applicationContext;

    @EventListener
    public void handleCustomEvent(CreateProgramEvent event) {
        if (event.getProgram().getRelations() == null) {
            return;
        }
        // 每个关系添加项目id
        event.getProgram().getRelations().forEach(f -> f.setProgramId(event.getProgram().getId()));
        // 新增关系
        long count = event.getProgram().getRelations().stream().filter(f -> {
            ProgramUser bean = applicationContext.getBean(ProgramUser.class);
            bean.setRole(f.getRole());
            bean.setProgramId(f.getProgramId());
            bean.setId(f.getId());
            bean.setUserId(f.getUserId());
            bean.save();
            return true;
        }).map(f -> 1).count();
        if (event.getProgram().getRelations().size() == count) {
            log.info("{} 项目关系添加成功 ", event.getProgram().getName());
        }
        // 为项目新建文件夹
        Folder bean = applicationContext.getBean(Folder.class);

        bean.setName(event.getProgram().getName());
        bean.setParentId(event.getProgram().getId());
        bean.setProgramId(event.getProgram().getId());
        bean.setId(event.getProgram().getId());
        bean.save();

        // 根据模板添加文件
    }
}
