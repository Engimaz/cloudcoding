package cn.edu.hcnu.program.application.service.impl;

import cn.edu.hcnu.program.application.assembler.AddProgramCommandToProgramMapping;
import cn.edu.hcnu.program.application.assembler.ProgramToProgramDTOMapping;
import cn.edu.hcnu.program.application.assembler.UpdateProgramCommandToProgramMapping;
import cn.edu.hcnu.program.application.service.IProgramApplication;
import cn.edu.hcnu.program.domain.config.CodeStoreConfig;
import cn.edu.hcnu.program.domain.service.code.ProgrammingLanguage;
import cn.edu.hcnu.program.domain.service.docker.DockerDomainService;
import cn.edu.hcnu.program.domain.service.file.File;
import cn.edu.hcnu.program.domain.service.file.FileDomainService;
import cn.edu.hcnu.program.domain.service.folder.Folder;
import cn.edu.hcnu.program.domain.service.folder.FolderDomainService;
import cn.edu.hcnu.program.domain.service.program.Program;
import cn.edu.hcnu.program.domain.service.program.ProgramDomainService;
import cn.edu.hcnu.program.domain.service.relation.ProgramUser;
import cn.edu.hcnu.program.domain.service.relation.ProgramUserDomainService;
import cn.edu.hcnu.program.model.command.AddProgramCommand;
import cn.edu.hcnu.program.model.command.ExecuteCommand;
import cn.edu.hcnu.program.model.command.UpdateProgramCommand;
import cn.edu.hcnu.program.model.dto.ExecutionInfoDTO;
import cn.edu.hcnu.program.model.dto.ProgramDTO;
import cn.edu.hcnu.program.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component("programApplicationImpl")
public class ProgramApplication implements IProgramApplication {


    @Autowired
    private AddProgramCommandToProgramMapping addProgramCommandToProgramMapping;

    @Autowired
    private UpdateProgramCommandToProgramMapping updateProgramCommandToProgramMapping;


    @Autowired
    private ProgramDomainService programDomainService;


    @Autowired
    private ProgramToProgramDTOMapping programToProgramDTOMapping;

    @Autowired
    private ProgramUserDomainService programUserDomainService;

    @Autowired
    private FolderDomainService folderDomainService;

    @Autowired
    private FileDomainService fileDomainService;

    @Autowired
    private CodeStoreConfig codeStoreConfig;

    @Autowired
    private DockerDomainService dockerDomainService;

    @Autowired
    private ApplicationContext applicationContext;


    @Override
    public ProgramDTO save(AddProgramCommand addProgramCommand) {
        Program program = addProgramCommandToProgramMapping.sourceToTarget(addProgramCommand);
        Program save = programDomainService.save(program);
        if (save == null) {
            return null;
        }
        return programToProgramDTOMapping.sourceToTarget(save);

    }

    @Override
    public ProgramDTO update(UpdateProgramCommand updateProgramCommand) {
        Program program = updateProgramCommandToProgramMapping.sourceToTarget(updateProgramCommand);
        Program update = programDomainService.update(program);
        if (update == null) {
            return null;
        }
        return programToProgramDTOMapping.sourceToTarget(update);
    }

    @Override
    public List<ProgramDTO> queryProgramByUserId(String userId) {
        // 查询用户的项目
        List<ProgramUser> users = programUserDomainService.queryRelationByUserId(userId);
        if (users == null || users.isEmpty()) {
            return null;
        }
        List<String> programIds = users.stream().map(ProgramUser::getProgramId).collect(Collectors.toList());
        List<Program> programs = programDomainService.queryProgramByProgramIds(programIds);

        // 查出项目的人
        for (Program program : programs) {
            program.setRelations(programUserDomainService.getProgramUserByProgramId(Long.valueOf(program.getId())));
        }

        return programToProgramDTOMapping.sourceToTarget(programs);

    }

    @Override
    public ExecutionInfoDTO execute(ExecuteCommand executeCommand) {
        // 查询项目
        Program program = programDomainService.queryById(executeCommand.getId());

        // 项目写盘
        writePath(program);

        // 输入数据写盘
        FileUtil.saveStringToFile(codeStoreConfig.getLocalRootPath() + program.getId() + "/" + program.getName() + "/" + program.getName() + ".in", executeCommand.getCommand());

        // 选择语言交给对应的代码执行器
        ProgrammingLanguage bean = applicationContext.getBean(program.getLanguage() + "ProgramingExecutor", ProgrammingLanguage.class);
        if (bean != null) {
            return bean.execute(program);
        }

        return null;
    }

    @Override
    public ExecutionInfoDTO stopContainer(String containerName) {
        return dockerDomainService.stopContainer(containerName);
    }

    private void writePath(Program program) {
        // 查询项目文件夹
        List<Folder> folders = folderDomainService.queryAllFolderByProgramId(program.getId());

        // 查找根id
        List<Folder> rootId = folders.stream().filter(f -> Objects.equals(f.getId(), f.getParentId())).collect(Collectors.toList());

        // 递归写盘
        writePath(folders, rootId, codeStoreConfig.getLocalRootPath() + program.getId() + "/");

    }

    private void writePath(List<Folder> folders, List<Folder> ids, String path) {

        for (Folder s : ids) {
            // 他的文件直接写进去
            List<File> files = fileDomainService.queryByFolderId(s.getId());
            for (File file : files) {
                String fileName = path + "/" + s.getName() + "/" + file.getName();
                FileUtil.saveStringToFile(fileName, file.getContent());
            }
            // 查找他的子文件夹
            List<Folder> childrenFolder = folders.stream().filter(f -> Objects.equals(f.getParentId(), s.getId()) && !Objects.equals(s.getId(), f.getId())).collect(Collectors.toList());
            if (!childrenFolder.isEmpty()) {
                writePath(folders, childrenFolder, path + "/" + s.getName() + "/");

            }

        }
    }
}
