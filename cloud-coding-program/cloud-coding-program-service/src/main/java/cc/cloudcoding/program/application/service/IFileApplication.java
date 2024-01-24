package cc.cloudcoding.program.application.service;

import cc.cloudcoding.program.model.command.AddFileCommand;
import cc.cloudcoding.program.model.command.UpdateFileCommand;
import cc.cloudcoding.program.model.dto.FileDTO;

public interface IFileApplication {
    FileDTO save(AddFileCommand addFileCommand);

    boolean remove(String id);


    FileDTO queryById(String id);

    FileDTO update(UpdateFileCommand updateFileCommand);
}
