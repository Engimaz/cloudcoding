package cn.edu.hcnu.program.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDTO {
    private String id;
    private String name;
    private String content;
    private String folderId;
}
