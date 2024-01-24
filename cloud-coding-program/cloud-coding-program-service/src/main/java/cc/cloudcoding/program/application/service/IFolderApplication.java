package cc.cloudcoding.program.application.service;

import cc.cloudcoding.program.model.command.AddFolderCommand;
import cc.cloudcoding.program.model.command.UpdateFolderCommand;
import cc.cloudcoding.program.model.dto.FolderDTO;

public interface IFolderApplication {
    FolderDTO addFolder(AddFolderCommand addFolderCommand);

    boolean removeFolder(String id);

    FolderDTO queryTopFolder(String id);

    FolderDTO queryFolder(String id);

    FolderDTO updateFolder(UpdateFolderCommand updateFolderCommand);
}
