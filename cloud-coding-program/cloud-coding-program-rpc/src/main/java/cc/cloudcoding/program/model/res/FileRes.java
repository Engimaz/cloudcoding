package cc.cloudcoding.program.model.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileRes {
    private String id;
    private String name;
    private String content;
    private String folderId;
}
