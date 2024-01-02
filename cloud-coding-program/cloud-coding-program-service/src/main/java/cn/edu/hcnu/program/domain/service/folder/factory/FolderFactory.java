package cn.edu.hcnu.program.domain.service.folder.factory;

import cn.edu.hcnu.id.domain.service.IDGenerator;
import cn.edu.hcnu.program.domain.service.folder.Folder;
import cn.edu.hcnu.program.model.po.FolderPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FolderFactory {

    @Autowired
    @Qualifier(value = "snowflake")
    private IDGenerator idGenerator;


    public FolderPO createFolderPo(Folder folder) {
        FolderPO folderPO = new FolderPO();
        if (folder == null || folder.getId() == null) {
            folderPO.setId(Long.valueOf(idGenerator.nextID()));
        } else {
            folderPO.setId(Long.valueOf(folder.getId()));
        }
        if (folder.getName() != null) {
            folderPO.setName(folder.getName());
        }
        if (folder.getParentId() != null) {
            folderPO.setParentId(Long.valueOf(folder.getParentId()));
        }
        if (folder.getProgramId() != null) {
            folderPO.setProgramId(Long.valueOf(folder.getProgramId()));
        }

        return folderPO;
    }

    public Folder createFolder(FolderPO folderPO) {
        Folder folder = new Folder();
        folder.setId(String.valueOf(folderPO.getId()));
        folder.setParentId(String.valueOf(folderPO.getParentId()));
        folder.setProgramId(String.valueOf(folderPO.getProgramId()));
        folder.setName(folderPO.getName());
        return folder;
    }

    public List<Folder> createFolder(List<FolderPO> list) {
        return list.stream().map(this::createFolder).collect(Collectors.toList());
    }
}
