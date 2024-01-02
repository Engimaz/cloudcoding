package cn.edu.hcnu.program.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FolderDTO {
    private String name;
    private String parentId;
    private String programId;
    private String id;
    private List<FolderDTO> folders;
    private List<FileDTO> files;
}
