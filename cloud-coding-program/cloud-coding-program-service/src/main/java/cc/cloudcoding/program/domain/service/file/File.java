package cc.cloudcoding.program.domain.service.file;

import cc.cloudcoding.program.infrastructure.repository.FileRepository;
import cc.cloudcoding.program.model.po.FilePO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
@Setter
@Scope("prototype")
public class File {


    private final FileRepository fileRepository;
    private String id;
    private String folderId;
    private String content;
    private String name;

    public void save() {
        FilePO filePO = new FilePO();

        filePO.setId(Long.valueOf(this.getId()));
        if (this.getContent() == null) {
            filePO.setContent("");
        } else {
            filePO.setContent(this.getContent());
        }
        if (this.getName() == null) {
            filePO.setName("未命名");
        } else {
            filePO.setName(this.getName());
        }

        filePO.setFolderId(Long.valueOf(this.getFolderId()));

        fileRepository.save(filePO);

    }

    public boolean remove() {
        return fileRepository.removeById(id);
    }

    public void render() {
        FilePO filePO = fileRepository.getById(id);
        this.setId(String.valueOf(filePO.getId()));
        this.setName(filePO.getName());
        this.setContent(filePO.getContent());
        this.setFolderId(String.valueOf(filePO.getFolderId()));
    }

    public void update() {
        FilePO filePO = new FilePO();

        filePO.setId(Long.valueOf(this.getId()));
        if (this.getContent() == null) {
            filePO.setContent("");
        } else {
            filePO.setContent(this.getContent());
        }
        if (this.getName() == null) {
            filePO.setName("未命名");
        } else {
            filePO.setName(this.getName());
        }

        filePO.setFolderId(Long.valueOf(this.getFolderId()));
        fileRepository.updateById(filePO);

    }
}
