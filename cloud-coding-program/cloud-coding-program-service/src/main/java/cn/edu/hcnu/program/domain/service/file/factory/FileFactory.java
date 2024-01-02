package cn.edu.hcnu.program.domain.service.file.factory;

import cn.edu.hcnu.id.domain.service.IDGenerator;
import cn.edu.hcnu.program.domain.service.file.File;
import cn.edu.hcnu.program.model.po.FilePO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileFactory {

    @Autowired
    @Qualifier("snowflake")
    private IDGenerator idGenerator;

    public FilePO createFilePO(File file) {
        FilePO filePO = new FilePO();
        if (file.getId() == null) {
            filePO.setId(Long.valueOf(idGenerator.nextID()));
        } else {
            filePO.setId(Long.valueOf(file.getId()));
        }
        if(file.getContent()==null){
            filePO.setContent("");
        }else{
            filePO.setContent(file.getContent());
        }
        if (file.getName() == null) {
            filePO.setName("未命名");
        }else{
            filePO.setName(file.getName());
        }

        filePO.setFolderId(Long.valueOf(file.getFolderId()));
        return filePO;
    }

    public File createFile(FilePO filePO) {
        File file = new File();
        file.setId(String.valueOf(filePO.getId()));
        file.setName(filePO.getName());
        file.setContent(filePO.getContent());
        file.setFolderId(String.valueOf(filePO.getFolderId()));
        return file;
    }
    public List<File> createFile(List<FilePO> list){
        return list.stream().map(this::createFile).collect(Collectors.toList());
    }
}
