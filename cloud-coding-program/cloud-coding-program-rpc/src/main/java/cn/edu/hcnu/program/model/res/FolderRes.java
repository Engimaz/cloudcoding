package cn.edu.hcnu.program.model.res;

import cn.edu.hcnu.program.model.dto.FileDTO;
import cn.edu.hcnu.program.model.dto.FolderDTO;
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
