package cc.cloudcoding.program.domain.service.folder;

import cc.cloudcoding.program.domain.event.folder.FolderPublisher;
import cc.cloudcoding.program.domain.service.file.File;
import cc.cloudcoding.program.infrastructure.repository.FileRepository;
import cc.cloudcoding.program.infrastructure.repository.FolderRepository;
import cc.cloudcoding.program.model.po.FilePO;
import cc.cloudcoding.program.model.po.FolderPO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Getter
@Setter
@Scope("prototype")
public class Folder {

    private final FolderRepository folderRepository;
    private final FileRepository fileRepository;


    private final FolderPublisher folderPublisher;
    private String name;
    private Long parentId;
    private Long projectId;
    private Long id;
    private List<Folder> folders;
    private List<File> files;
    private final ApplicationContext applicationContext;

    public void save() {
        FolderPO folderPO = new FolderPO();

        folderPO.setId(this.getId());
        if (this.getName() != null) {
            folderPO.setName(this.getName());
        }
        if (this.getParentId() != null) {
            folderPO.setParentId(this.getParentId());
        }
        if (this.getProjectId() != null) {
            folderPO.setProjectId(this.getProjectId());
        }
        folderPublisher.publishCreateEvent(this);
        folderRepository.save(folderPO);

    }

    public boolean remove() {
        boolean b = folderRepository.removeById(this.id);
        if (b) {
            // 发布删除文件夹事件 使得子文件删除
            folderPublisher.publishRemoveEvent(this);
        }
        return b;
    }


    public void update() {
        FolderPO folderPO = new FolderPO();

        folderPO.setId(this.getId());
        if (this.getName() != null) {
            folderPO.setName(this.getName());
        }
        if (this.getParentId() != null) {
            folderPO.setParentId(this.getParentId());
        }
        if (this.getProjectId() != null) {
            folderPO.setProjectId(this.getProjectId());
        }
        folderPublisher.publishUpdateEvent(this);
        folderRepository.updateById(folderPO);

    }


    public void render() {
        FolderPO folderPO = folderRepository.getById(this.id);
        this.setId(folderPO.getId());
        this.setParentId(folderPO.getParentId());
        this.setProjectId(folderPO.getProjectId());
        this.setName(folderPO.getName());
        // 装配他的子文件夹
        List<FolderPO> folderPOList = folderRepository.getFoldersByParentId(this.id);
        if (folderPOList.isEmpty()) {
            this.setFolders(new ArrayList<>());
        } else {
            List<Folder> collect = folderPOList.stream().map(po -> {
                Folder bean = applicationContext.getBean(Folder.class);
                bean.setId(po.getId());
                bean.render();
                return bean;
            }).collect(Collectors.toList());

            this.setFolders(collect);
        }

        List<FilePO> filePOList = fileRepository.getFilesByFolderId(this.id);
        if (filePOList.isEmpty()) {
            this.setFiles(new ArrayList<>());
        } else {
            List<File> collect = filePOList.stream().map(po -> {
                File bean = applicationContext.getBean(File.class);
                bean.setId(String.valueOf(po.getId()));
                bean.setName(po.getName());
                bean.setFolderId(String.valueOf(po.getFolderId()));
                bean.setName(po.getName());
                return bean;
            }).collect(Collectors.toList());

            this.setFiles(collect);
        }

    }


}
