package cc.cloudcoding.resource.domain.service.minio;

import cc.cloudcoding.resource.domain.service.factory.product.OssResource;
import lombok.Data;

@Data
public class MinioResource extends OssResource {
    private String bucket;

}
