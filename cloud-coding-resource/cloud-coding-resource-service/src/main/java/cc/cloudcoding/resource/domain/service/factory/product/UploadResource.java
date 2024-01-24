package cc.cloudcoding.resource.domain.service.factory.product;

import cc.cloudcoding.resource.model.command.UploadResourceCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadResource {
    private UploadResourceCommand uploadResourceCommand;
    private String bucket;
    private String path;
    private String userId;
}
