package cc.cloudcoding.program.model.res;

import cc.cloudcoding.program.model.dto.FileDTO;
import cc.cloudcoding.program.model.dto.FolderDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FolderRes {
    private String name;
    private String parentId;
    private String programId;
    private String id;
    private List<FolderDTO> folders;
    private List<FileDTO> files;
}
