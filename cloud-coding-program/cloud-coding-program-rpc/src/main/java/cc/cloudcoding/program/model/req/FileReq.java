package cc.cloudcoding.program.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileReq {
    private String id;
    private String content;
    private String name;
    private String folderId;
}
