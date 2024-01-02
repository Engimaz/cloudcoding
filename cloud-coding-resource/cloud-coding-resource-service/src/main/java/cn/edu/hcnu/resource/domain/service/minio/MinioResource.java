package cn.edu.hcnu.resource.domain.service.minio;

import cn.edu.hcnu.resource.domain.service.factory.product.OssResource;
import lombok.Data;

@Data
public class MinioResource extends OssResource {
    private String bucket;

}
