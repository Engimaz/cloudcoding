package cn.edu.hcnu.program.application.service.impl;

import cn.edu.hcnu.program.application.assembler.AddFolderCommandToFolderMapping;
import cn.edu.hcnu.program.application.assembler.FileToFileDTOMapping;
import cn.edu.hcnu.program.application.assembler.FolderToFolderDTOMapping;
import cn.edu.hcnu.program.application.assembler.UpdateFolderCommandToFolderMapping;
import cn.edu.hcnu.program.application.service.IFolderApplication;
import cn.edu.hcnu.program.domain.service.file.File;
import cn.edu.hcnu.program.domain.service.file.FileDomainService;
import cn.edu.hcnu.program.domain.service.folder.Folder;
import cn.edu.hcnu.program.domain.service.folder.FolderDomainService;
import cn.edu.hcnu.program.model.command.AddFolderCommand;
import cn.edu.hcnu.program.model.command.UpdateFolderCommand;
import cn.edu.hcnu.program.model.dto.FolderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;

@Component
public class FolderApplicationImpl implements IFolderApplication {

    @Autowired
    private FolderDomainService folderDomainService;

    @Autowired
    private FileDomainService fileDomainService;

    @Autowired
    private AddFolderCommandToFolderMapping addFolderCommandToFolderMapping;

    @Autowired
    private FolderToFolderDTOMapping folderToFolderDTOMapping;

    @Autowired
    private FileToFileDTOMapping fileToFileDTOMapping;

    @Autowired
    private UpdateFolderCommandToFolderMapping updateFolderCommandToFolderMapping;

    @Override
    public FolderDTO addFolder(AddFolderCommand addFolderCommand) {
        Folder folder = addFolderCommandToFolderMapping.sourceToTarget(addFolderCommand);

        Folder save = folderDomainService.save(folder);

        if (save != null) {
            return folderToFolderDTOMapping.sourceToTarget(save);
        }

        return null;
    }

    @Override
    public boolean removeFolder(String id) {
        boolean res = folderDomainService.remove(id);
        return false;
    }

    @Override
    public FolderDTO queryTopFolder(String id) {
        Folder self = folderDomainService.queryTopFolderByProgramId(id);
        return getChildren(self);
    }

    @Override
    public FolderDTO queryFolder(String id) {
        Folder self = folderDomainService.queryById(id);
        return getChildren(self);
    }

    @NotNull
    private FolderDTO getChildren(Folder self) {
        List<Folder> children = folderDomainService.queryChildrenFolderById(self.getId());
        List<File> files = fileDomainService.queryByFolderId(self.getId());
        FolderDTO folderDTO = folderToFolderDTOMapping.sourceToTarget(self);
        folderDTO.setFolders(folderToFolderDTOMapping.sourceToTarget(children));
        folderDTO.setFiles(fileToFileDTOMapping.sourceToTarget(files));
        return folderDTO;
    }

    @Override
    public FolderDTO updateFolder(UpdateFolderCommand updateFolderCommand) {
        Folder folder = folderDomainService.update(updateFolderCommandToFolderMapping.sourceToTarget(updateFolderCommand));
        if (folder != null) {
            return folderToFolderDTOMapping.sourceToTarget(folder);
        }
        return null;
    }
}
