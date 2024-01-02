package cn.edu.hcnu.program.domain.service.folder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Folder {
    private String name;
    private String parentId;
    private String programId;
    private String id;
}
