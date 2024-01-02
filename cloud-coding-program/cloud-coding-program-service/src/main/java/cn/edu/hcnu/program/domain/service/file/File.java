package cn.edu.hcnu.program.domain.service.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class File {
    private String id;
    private String folderId;
    private String content;
    private String name;
}
