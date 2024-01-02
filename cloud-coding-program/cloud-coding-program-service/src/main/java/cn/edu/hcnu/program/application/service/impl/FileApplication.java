package cn.edu.hcnu.program.application.service.impl;

import cn.edu.hcnu.program.application.assembler.AddFileCommandToFileMapping;
import cn.edu.hcnu.program.application.assembler.FileToFileDTOMapping;
import cn.edu.hcnu.program.application.assembler.UpdateFileCommandToFileMapping;
import cn.edu.hcnu.program.application.service.IFileApplication;
import cn.edu.hcnu.program.domain.service.file.File;
import cn.edu.hcnu.program.domain.service.file.FileDomainService;
import cn.edu.hcnu.program.model.command.AddFileCommand;
import cn.edu.hcnu.program.model.command.UpdateFileCommand;
import cn.edu.hcnu.program.model.dto.FileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileApplication implements IFileApplication {

    @Autowired
    private AddFileCommandToFileMapping fileCommandToFileMapping;

    @Autowired
    private FileDomainService fileDomainService;

    @Autowired
    private FileToFileDTOMapping fileToFileDTOMapping;

    @Autowired
    private UpdateFileCommandToFileMapping updateFileCommandToFileMapping;

    @Override
    public FileDTO save(AddFileCommand addFileCommand) {
        File file = fileCommandToFileMapping.sourceToTarget(addFileCommand);
        File save = fileDomainService.save(file);
        if (save != null) {
            return fileToFileDTOMapping.sourceToTarget(save);
        }
        return null;
    }

    @Override
    public boolean remove(String id) {
        return fileDomainService.removeById(id);
    }

    @Override
    public FileDTO queryById(String id) {
        return fileToFileDTOMapping.sourceToTarget(fileDomainService.queryById(id));
    }

    @Override
    public FileDTO update(UpdateFileCommand updateFileCommand) {
        File update = fileDomainService.update(updateFileCommandToFileMapping.sourceToTarget(updateFileCommand));
        if (update != null) {
            return fileToFileDTOMapping.sourceToTarget(update);
        }
        return null;
    }
}
