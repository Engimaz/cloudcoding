package cc.cloudcoding.resource.model.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadResourceCommand {
    private String uploadResourceAbsolutePath;
    private String userId;
    private String chunkMd5;
    private String md5;
    private String contentType;
    private String name;
    private Long size;
    private String id;
}
