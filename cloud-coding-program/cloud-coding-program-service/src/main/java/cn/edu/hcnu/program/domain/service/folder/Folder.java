package cn.edu.hcnu.program.domain.service.folder;

import cn.edu.hcnu.program.domain.event.folder.FolderPublisher;
import cn.edu.hcnu.program.infrastructure.repository.FolderRepository;
import cn.edu.hcnu.program.model.po.FolderPO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Getter
@Setter
public class Folder {

    private final FolderRepository folderRepository;


    private final FolderPublisher folderPublisher;
    private String name;
    private String parentId;
    private String programId;
    private String id;

    public void save() {
        FolderPO folderPO = new FolderPO();

        folderPO.setId(Long.valueOf(this.getId()));
        if (this.getName() != null) {
            folderPO.setName(this.getName());
        }
        if (this.getParentId() != null) {
            folderPO.setParentId(Long.valueOf(this.getParentId()));
        }
        if (this.getProgramId() != null) {
            folderPO.setProgramId(Long.valueOf(this.getProgramId()));
        }
        folderRepository.save(folderPO);

    }

    public boolean remove() {
        boolean b = folderRepository.removeById(this.id);
        if (b) {
            // 发布删除文件夹事件 使得子文件删除
            folderPublisher.pushliRemoveEvent(this.id);
        }
        return b;
    }


    public void update() {
        FolderPO folderPO = new FolderPO();

        folderPO.setId(Long.valueOf(this.getId()));
        if (this.getName() != null) {
            folderPO.setName(this.getName());
        }
        if (this.getParentId() != null) {
            folderPO.setParentId(Long.valueOf(this.getParentId()));
        }
        if (this.getProgramId() != null) {
            folderPO.setProgramId(Long.valueOf(this.getProgramId()));
        }
        folderRepository.updateById(folderPO);

    }


    public void render() {
        FolderPO folderPO = folderRepository.getById(id);
        this.setId(String.valueOf(folderPO.getId()));
        this.setParentId(String.valueOf(folderPO.getParentId()));
        this.setProgramId(String.valueOf(folderPO.getProgramId()));
        this.setName(folderPO.getName());
    }


}
