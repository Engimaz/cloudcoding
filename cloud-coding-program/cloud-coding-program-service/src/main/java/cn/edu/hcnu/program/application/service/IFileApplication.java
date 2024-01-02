package cn.edu.hcnu.program.application.service;

import cn.edu.hcnu.program.model.command.AddFileCommand;
import cn.edu.hcnu.program.model.command.UpdateFileCommand;
import cn.edu.hcnu.program.model.dto.FileDTO;

public interface IFileApplication {
    FileDTO save(AddFileCommand addFileCommand);

    boolean remove(String id);


    FileDTO queryById(String id);

    FileDTO update(UpdateFileCommand updateFileCommand);
}
