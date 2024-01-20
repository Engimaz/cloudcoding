package cn.edu.hcnu.program.application.service.impl;

import cn.edu.hcnu.id.domain.service.IDGenerator;
import cn.edu.hcnu.program.application.assembler.ProgramToProgramDTOMapping;
import cn.edu.hcnu.program.application.service.IProgramApplication;
import cn.edu.hcnu.program.domain.config.CodeStoreConfig;
import cn.edu.hcnu.program.domain.service.code.ProgrammingLanguage;
import cn.edu.hcnu.program.domain.service.docker.Docker;
import cn.edu.hcnu.program.domain.service.file.File;
import cn.edu.hcnu.program.domain.service.folder.Folder;
import cn.edu.hcnu.program.domain.service.program.Program;
import cn.edu.hcnu.program.domain.service.relation.ProgramUser;
import cn.edu.hcnu.program.infrastructure.repository.FileRepository;
import cn.edu.hcnu.program.infrastructure.repository.FolderRepository;
import cn.edu.hcnu.program.infrastructure.repository.ProgramRepository;
import cn.edu.hcnu.program.infrastructure.repository.ProgramUserRepository;
import cn.edu.hcnu.program.model.command.AddProgramCommand;
import cn.edu.hcnu.program.model.command.ExecuteCommand;
import cn.edu.hcnu.program.model.command.UpdateProgramCommand;
import cn.edu.hcnu.program.model.dto.ExecutionInfoDTO;
import cn.edu.hcnu.program.model.dto.ProgramDTO;
import cn.edu.hcnu.program.model.po.FilePO;
import cn.edu.hcnu.program.model.po.FolderPO;
import cn.edu.hcnu.program.model.po.ProgramPO;
import cn.edu.hcnu.program.model.po.ProgramUserPO;
import cn.edu.hcnu.program.util.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component("programApplicationImpl")
@RequiredArgsConstructor
public class ProgramApplication implements IProgramApplication {


    private final ProgramToProgramDTOMapping programToProgramDTOMapping;


    private final FolderRepository folderRepository;

    private final CodeStoreConfig codeStoreConfig;

    private final Docker docker;

    private final ApplicationContext applicationContext;

    private final ProgramRepository programRepository;

    private final ProgramUserRepository programUserRepository;

    @Autowired
    @Qualifier("snowflake")
    private IDGenerator idGenerator;

    @Override
    public ProgramDTO save(AddProgramCommand addProgramCommand) {
        Program program = applicationContext.getBean(Program.class);
        program.setName(addProgramCommand.getName());
        program.setId(idGenerator.nextID());
        program.setDescription(addProgramCommand.getDescription());
        program.setLanguage(addProgramCommand.getLanguage());
        program.setSdk(addProgramCommand.getSdk());
        program.setAvatar(addProgramCommand.getAvatar());
        List<ProgramUser> collect = addProgramCommand.getRelations().stream().map(
                f -> {
                    ProgramUser bean = applicationContext.getBean(ProgramUser.class);
                    bean.setRole(f.getRole());
                    bean.setProgramId(f.getProgramId());
                    bean.setId(Long.valueOf(idGenerator.nextID()));
                    bean.setUserId(f.getUserId());
                    return bean;
                }
        ).collect(Collectors.toList());
        program.setRelations(collect);
        program.save();

        return programToProgramDTOMapping.sourceToTarget(program);

    }

    @Override
    public ProgramDTO update(UpdateProgramCommand updateProgramCommand) {
        Program program = applicationContext.getBean(Program.class);
        program.setName(updateProgramCommand.getName());
        program.setId(updateProgramCommand.getId());
        program.setDescription(updateProgramCommand.getDescription());
        program.setLanguage(updateProgramCommand.getLanguage());
        program.setSdk(updateProgramCommand.getSdk());
        program.setAvatar(updateProgramCommand.getAvatar());
        List<ProgramUser> collect = updateProgramCommand.getRelations().stream().map(
                f -> {
                    ProgramUser bean = applicationContext.getBean(ProgramUser.class);
                    bean.setRole(f.getRole());
                    bean.setProgramId(f.getProgramId());
                    bean.setId(Long.valueOf(idGenerator.nextID()));
                    bean.setUserId(f.getUserId());
                    return bean;
                }
        ).collect(Collectors.toList());
        program.setRelations(collect);
        program.update();

        return programToProgramDTOMapping.sourceToTarget(program);
    }

    @Override
    public List<ProgramDTO> queryProgramByUserId(String userId) {
        // 查询用户的项目
        List<ProgramUserPO> list1 = programUserRepository.list(new LambdaQueryWrapper<ProgramUserPO>().eq(ProgramUserPO::getUserId, Long.valueOf(userId)));
        List<ProgramUser> users = list1.stream().map(
                programUserPO -> {
                    ProgramUser programUser = applicationContext.getBean(ProgramUser.class);
                    programUser.setId(programUserPO.getId());
                    programUser.setUserId(String.valueOf(programUserPO.getUserId()));
                    programUser.setProgramId(String.valueOf(programUserPO.getProgramId()));
                    programUser.setRole(programUserPO.getRole());
                    return programUser;
                }
        ).collect(Collectors.toList());

        if (users.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> programIds = users.stream().map(ProgramUser::getProgramId).collect(Collectors.toList());
        List<ProgramPO> programPOS = programRepository.listByIds(programIds);
        List<Program> programs = programPOS.stream().map(
                programPO -> {
                    Program program = applicationContext.getBean(Program.class);
                    program.setId(String.valueOf(programPO.getId()));
                    program.setName(programPO.getName());
                    program.setLanguage(programPO.getLanguage());
                    program.setSdk(programPO.getSdk());
                    program.setAvatar(programPO.getAvatar());
                    program.setDescription(programPO.getDescription());
                    return program;
                }
        ).collect(Collectors.toList());

        // 查出项目的人
        for (Program program : programs) {
            List<ProgramUserPO> list = programUserRepository.list(new LambdaQueryWrapper<ProgramUserPO>().eq(ProgramUserPO::getProgramId, program.getId()));
            List<ProgramUser> collect = list.stream().map(
                    programUserPO -> {
                        ProgramUser programUser = applicationContext.getBean(ProgramUser.class);
                        programUser.setId(programUserPO.getId());
                        programUser.setUserId(String.valueOf(programUserPO.getUserId()));
                        programUser.setProgramId(String.valueOf(programUserPO.getProgramId()));
                        programUser.setRole(programUserPO.getRole());
                        return programUser;
                    }
            ).collect(Collectors.toList());
            program.setRelations(collect);
        }

        return programToProgramDTOMapping.sourceToTarget(programs);

    }

    @Override
    public ExecutionInfoDTO execute(ExecuteCommand executeCommand) {
        Program program = applicationContext.getBean(Program.class);
        program.setId(executeCommand.getId());
        // 查询项目
        program.render();

        // 项目写盘
        writePath(program);

        // 输入数据写盘
        FileUtil.saveStringToFile(codeStoreConfig.getLocalRootPath() + program.getId() + "/" + program.getName() + "/" + program.getName() + ".in", executeCommand.getCommand());

        // 选择语言交给对应的代码执行器
        ProgrammingLanguage bean = applicationContext.getBean(program.getLanguage() + "ProgramingExecutor", ProgrammingLanguage.class);
        return bean.execute(program);

    }

    @Override
    public ExecutionInfoDTO stopContainer(String containerName) {
        return docker.stopContainer(containerName);
    }



    private void writePath(Program program) {
        // 查询项目文件夹
        List<FolderPO> list = folderRepository.list(new LambdaQueryWrapper<FolderPO>().eq(FolderPO::getProgramId, program.getId()));
        List<Folder> folders = list.stream().map(
                folderPO -> {
                    Folder folder = applicationContext.getBean(Folder.class);
                    folder.setId(String.valueOf(folderPO.getId()));
                    folder.setParentId(String.valueOf(folderPO.getParentId()));
                    folder.setProgramId(String.valueOf(folderPO.getProgramId()));
                    folder.setName(folderPO.getName());
                    return folder;
                }
        ).collect(Collectors.toList());
        // 查找根id
        List<Folder> rootId = folders.stream().filter(f -> Objects.equals(f.getId(), f.getParentId())).collect(Collectors.toList());

        // 递归写盘
        writePath(folders, rootId, codeStoreConfig.getLocalRootPath() + program.getId() + "/");

    }

    @Autowired
    private FileRepository fileRepository;

    private void writePath(List<Folder> folders, List<Folder> ids, String path) {

        for (Folder s : ids) {
            // 他的文件直接写进去
            List<FilePO> list = fileRepository.list(new LambdaQueryWrapper<FilePO>().eq(FilePO::getFolderId, s.getId()));
            List<File> files = list.stream().map(
                    filePO -> {
                        File file = applicationContext.getBean(File.class);
                        file.setId(String.valueOf(filePO.getId()));
                        file.setName(filePO.getName());
                        file.setContent(filePO.getContent());
                        file.setFolderId(String.valueOf(filePO.getFolderId()));
                        return file;
                    }
            ).collect(Collectors.toList());
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
