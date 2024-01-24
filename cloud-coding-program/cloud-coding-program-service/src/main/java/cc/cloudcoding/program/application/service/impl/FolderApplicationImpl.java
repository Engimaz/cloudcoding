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
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FolderApplicationImpl implements IFolderApplication {


    private final FolderToFolderDTOMapping folderToFolderDTOMapping;

    private final FileToFileDTOMapping fileToFileDTOMapping;
    private final FolderRepository folderRepository;

    private final ApplicationContext applicationContext;

    @Autowired
    @Qualifier(value = "snowflake")
    private IDGenerator idGenerator;

    @Override
    public FolderDTO addFolder(AddFolderCommand addFolderCommand) {
        Folder bean = applicationContext.getBean(Folder.class);
        bean.setId(idGenerator.nextID());
        bean.setName(addFolderCommand.getName());
        bean.setParentId(addFolderCommand.getParentId());
        bean.setProgramId(addFolderCommand.getProgramId());
        bean.save();
        return folderToFolderDTOMapping.sourceToTarget(bean);
    }

    @Override
    public boolean removeFolder(String id) {
        Folder bean = applicationContext.getBean(Folder.class);
        bean.setId(id);
        bean.remove();
        return true;
    }


    @Override
    public FolderDTO queryTopFolder(String id) {
        FolderPO folderPO = Objects.requireNonNull(Objects.requireNonNull(folderRepository.list(new LambdaQueryWrapper<FolderPO>()
                                .eq(FolderPO::getProgramId, id))
                        .stream()
                        .filter(f -> Objects.equals(f.getId(), f.getParentId())))
                .findFirst().orElse(null));
        Folder folder = applicationContext.getBean(Folder.class);
        folder.setId(String.valueOf(folderPO.getId()));
        folder.setParentId(String.valueOf(folderPO.getParentId()));
        folder.setProgramId(String.valueOf(folderPO.getProgramId()));
        folder.setName(folderPO.getName());
        return getChildren(folder);
    }

    @Override
    public FolderDTO queryFolder(String id) {
        Folder bean = applicationContext.getBean(Folder.class);
        bean.setId(id);
        bean.render();
        return getChildren(bean);
    }

    @Autowired
    private FileRepository fileRepository;

    @NotNull
    private FolderDTO getChildren(Folder self) {
        List<FolderPO> list = folderRepository.list(new LambdaQueryWrapper<FolderPO>().eq(FolderPO::getParentId, self.getId()).ne(FolderPO::getId, self.getId()));
        List<Folder> collect = list.stream().map(
                folderPO -> {
                    Folder folder = applicationContext.getBean(Folder.class);
                    folder.setId(String.valueOf(folderPO.getId()));
                    folder.setParentId(String.valueOf(folderPO.getParentId()));
                    folder.setProgramId(String.valueOf(folderPO.getProgramId()));
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
        bean.setId(updateFolderCommand.getId());
        bean.setName(updateFolderCommand.getName());
        bean.setParentId(updateFolderCommand.getParentId());
        bean.setProgramId(updateFolderCommand.getProgramId());
        bean.update();
        return folderToFolderDTOMapping.sourceToTarget(bean);
    }
}
