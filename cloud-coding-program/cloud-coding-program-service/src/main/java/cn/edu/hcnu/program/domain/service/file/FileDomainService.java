package cn.edu.hcnu.program.domain.service.file;

import cn.edu.hcnu.program.domain.service.file.factory.FileFactory;
import cn.edu.hcnu.program.infrastructure.repository.FileRepository;
import cn.edu.hcnu.program.model.po.FilePO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileDomainService {

    @Autowired
    private FileFactory fileFactory;

    @Autowired
    private FileRepository fileRepository;

    public boolean removeByFolderId(String folderId) {
        return fileRepository.remove(new LambdaQueryWrapper<FilePO>().eq(FilePO::getFolderId, folderId));
    }

    public List<File> queryByFolderId(String id) {
        return fileFactory.createFile(fileRepository.list(new LambdaQueryWrapper<FilePO>().eq(FilePO::getFolderId, id)));
    }

    public File save(File file) {

        FilePO filePO = fileFactory.createFilePO(file);
        boolean save = fileRepository.save(filePO);
        if (save) {
            return fileFactory.createFile(filePO);
        }
        return null;
    }

    public boolean removeById(String id) {
        return fileRepository.removeById(id);
    }

    public File queryById(String id) {
        return fileFactory.createFile(fileRepository.getById(id));
    }

    public File update(File file) {
        FilePO filePO = fileFactory.createFilePO(file);
        boolean b = fileRepository.updateById(filePO);
        if (b) {
            return fileFactory.createFile(filePO);
        }
        return null;
    }
}
