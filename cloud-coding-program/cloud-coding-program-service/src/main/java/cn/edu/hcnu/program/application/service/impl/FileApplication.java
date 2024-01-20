package cn.edu.hcnu.program.application.service.impl;

import cn.edu.hcnu.id.domain.service.IDGenerator;
import cn.edu.hcnu.program.application.assembler.FileToFileDTOMapping;
import cn.edu.hcnu.program.application.service.IFileApplication;
import cn.edu.hcnu.program.domain.service.file.File;
import cn.edu.hcnu.program.model.command.AddFileCommand;
import cn.edu.hcnu.program.model.command.UpdateFileCommand;
import cn.edu.hcnu.program.model.dto.FileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FileApplication implements IFileApplication {


    @Autowired
    private FileToFileDTOMapping fileToFileDTOMapping;


    @Autowired
    @Qualifier("snowflake")
    private IDGenerator idGenerator;

    @Override
    public FileDTO save(AddFileCommand addFileCommand) {
        File bean = applicationContext.getBean(File.class);
        bean.setId(idGenerator.nextID());
        bean.setName(addFileCommand.getName());
        bean.setContent(addFileCommand.getContent());
        bean.setFolderId(addFileCommand.getFolderId());
        bean.save();
        return fileToFileDTOMapping.sourceToTarget(bean);
    }

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public boolean remove(String id) {
        File bean = applicationContext.getBean(File.class);
        bean.setId(id);
        bean.remove();
        return true;
    }

    @Override
    public FileDTO queryById(String id) {
        File bean = applicationContext.getBean(File.class);
        bean.setId(id);
        bean.render();
        return fileToFileDTOMapping.sourceToTarget(bean);
    }

    @Override
    public FileDTO update(UpdateFileCommand updateFileCommand) {
        File bean = applicationContext.getBean(File.class);
        bean.setId(updateFileCommand.getId());
        bean.setName(updateFileCommand.getName());
        bean.setContent(updateFileCommand.getContent());
        bean.setFolderId(updateFileCommand.getFolderId());
        bean.update();
        return fileToFileDTOMapping.sourceToTarget(bean);
    }
}
