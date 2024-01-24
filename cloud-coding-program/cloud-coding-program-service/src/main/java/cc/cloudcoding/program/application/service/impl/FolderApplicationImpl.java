package cc.cloudcoding.program.application.service.impl;

import cc.cloudcoding.id.domain.service.IDGenerator;
import cc.cloudcoding.program.application.assembler.FileToFileDTOMapping;
import cc.cloudcoding.program.application.assembler.FolderToFolderDTOMapping;
import cc.cloudcoding.program.application.service.IFolderApplication;
import cc.cloudcoding.program.domain.service.file.File;
import cc.cloudcoding.program.domain.service.folder.Folder;
import cc.cloudcoding.program.infrastructure.repository.FileRepository;
import cc.cloudcoding.program.infrastructure.repository.FolderRepository;
import cc.cloudcoding.program.model.command.AddFolderCommand;
import cc.cloudcoding.program.model.command.UpdateFileCommand;
import cc.cloudcoding.program.model.command.UpdateFolderCommand;
import cc.cloudcoding.program.model.dto.FolderDTO;
import cc.cloudcoding.program.model.po.FilePO;
import cc.cloudcoding.program.model.po.FolderPO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FolderApplicationImpl implements IFolderApplication {


    private final FolderToFolderDTOMapping folderToFolderDTOMapping;
    private final FileRepository fileRepository;

    private final FileToFileDTOMapping fileToFileDTOMapping;
    private final FolderRepository folderRepository;

    private final ApplicationContext applicationContext;

    @Autowired
    @Qualifier(value = "snowflake")
    private IDGenerator idGenerator;

    @Override
    public FolderDTO addFolder(AddFolderCommand addFolderCommand) {
        Folder bean = applicationContext.getBean(Folder.class);
        Long id = Long.valueOf(idGenerator.nextID());
        bean.setId(id);
        bean.setName(addFolderCommand.getName());
        bean.setParentId(Long.valueOf(addFolderCommand.getParentId()));
        bean.setProjectId(Long.valueOf(addFolderCommand.getProjectId()));
        List<Folder> folders = folderAdapt(addFolderCommand.getFolders(), id);
        List<File> files = fileAdapt(addFolderCommand.getFiles(), id);
        bean.setFolders(folders);
        bean.setFiles(files);

        bean.save();
        return folderToFolderDTOMapping.sourceToTarget(bean);
    }

    @Override
    public boolean removeFolder(String id) {
        Folder bean = applicationContext.getBean(Folder.class);
        bean.setId(Long.valueOf(id));
        bean.remove();
        return true;
    }


    @Override
    public FolderDTO queryTopFolder(String id) {
        FolderPO folderPO = Objects.requireNonNull(Objects.requireNonNull(folderRepository.list(new LambdaQueryWrapper<FolderPO>()
                                .eq(FolderPO::getProjectId, id))
                        .stream()
                        .filter(f -> Objects.equals(f.getId(), f.getParentId())))
                .findFirst().orElse(null));
        Folder folder = applicationContext.getBean(Folder.class);
        folder.setId(folderPO.getId());
        folder.setParentId((folderPO.getParentId()));
        folder.setProjectId((folderPO.getProjectId()));
        folder.setName(folderPO.getName());
        return getChildren(folder);
    }

    @Override
    public FolderDTO queryFolder(String id) {
        Folder bean = applicationContext.getBean(Folder.class);
        bean.setId(Long.valueOf(id));
        bean.render();
        return getChildren(bean);
    }


    @NotNull
    private FolderDTO getChildren(Folder self) {
        List<FolderPO> list = folderRepository.list(new LambdaQueryWrapper<FolderPO>().eq(FolderPO::getParentId, self.getId()).ne(FolderPO::getId, self.getId()));
        List<Folder> collect = list.stream().map(
                folderPO -> {
                    Folder folder = applicationContext.getBean(Folder.class);
                    folder.setId(folderPO.getId());
                    folder.setParentId((folderPO.getParentId()));
                    folder.setProjectId((folderPO.getProjectId()));
                    folder.setName(folderPO.getName());
                    return folder;
                }
        ).collect(Collectors.toList());
        List<FilePO> filePOList = fileRepository.list(new LambdaQueryWrapper<FilePO>().eq(FilePO::getFolderId, self.getId()));
        List<File> files = filePOList.stream().map(
                filePO -> {
                    File file = applicationContext.getBean(File.class);
                    file.setId(String.valueOf(filePO.getId()));
                    file.setName(filePO.getName());
                    file.setContent(filePO.getContent());
                    file.setFolderId(String.valueOf(filePO.getFolderId()));
                    return file;
                }
        ).collect(Collectors.toList());
        FolderDTO folderDTO = folderToFolderDTOMapping.sourceToTarget(self);
        folderDTO.setFolders(folderToFolderDTOMapping.sourceToTarget(collect));
        folderDTO.setFiles(fileToFileDTOMapping.sourceToTarget(files));
        return folderDTO;
    }

    @Override
    public FolderDTO updateFolder(UpdateFolderCommand updateFolderCommand) {
        Folder bean = applicationContext.getBean(Folder.class);
        Long id = Long.valueOf(updateFolderCommand.getId());
        bean.setId(id);
        bean.setName(updateFolderCommand.getName());
        bean.setParentId(Long.valueOf(updateFolderCommand.getParentId()));
        bean.setProjectId(Long.valueOf(updateFolderCommand.getProjectId()));
        List<Folder> folders = folderAdapt(updateFolderCommand.getFolders(), id);
        List<File> files = fileAdapt(updateFolderCommand.getFiles(), id);
        bean.setFolders(folders);
        bean.setFiles(files);
        bean.update();
        return folderToFolderDTOMapping.sourceToTarget(bean);
    }

    private List<Folder> folderAdapt(List<UpdateFolderCommand> folders, Long pid) {
        LinkedList<Folder> res = new LinkedList<>();
        for (UpdateFolderCommand folder : folders) {
            Long id = Long.valueOf(idGenerator.nextID());
            Folder bean = applicationContext.getBean(Folder.class);
            bean.setParentId(Long.valueOf(String.valueOf(pid)));
            bean.setId(id);
            bean.setName(folder.getName());
            bean.setProjectId(Long.valueOf(folder.getProjectId()));
            bean.setFolders(folderAdapt(folder.getFolders(), id));
            bean.setFiles(fileAdapt(folder.getFiles(), id));
            res.add(bean);
        }

        return res;
    }

    private List<File> fileAdapt(List<UpdateFileCommand> files, Long id) {
        LinkedList<File> res = new LinkedList<>();
        for (UpdateFileCommand file : files) {
            File bean = applicationContext.getBean(File.class);
            bean.setName(file.getName());
            bean.setFolderId(String.valueOf(id));
            if (isInteger(file.getId())) {
                bean.setId(file.getId());
            } else {
                bean.setId(idGenerator.nextID());
            }
            bean.setContent(file.getContent());
            res.add(bean);
        }
        return res;
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

}
