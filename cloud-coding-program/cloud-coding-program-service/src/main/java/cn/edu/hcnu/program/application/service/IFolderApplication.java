package cn.edu.hcnu.program.application.service;

import cn.edu.hcnu.program.model.command.AddFolderCommand;
import cn.edu.hcnu.program.model.command.UpdateFolderCommand;
import cn.edu.hcnu.program.model.dto.FolderDTO;

public interface IFolderApplication {
    FolderDTO addFolder(AddFolderCommand addFolderCommand);

    boolean removeFolder(String id);

    FolderDTO queryTopFolder(String id);

    FolderDTO queryFolder(String id);

    FolderDTO updateFolder(UpdateFolderCommand updateFolderCommand);
}
