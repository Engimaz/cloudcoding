package cc.cloudcoding.resource.domain.service.minio;

import cc.cloudcoding.base.execption.CloudCodingException;
import cc.cloudcoding.resource.domain.service.factory.AbstractOssFactory;
import cc.cloudcoding.resource.domain.service.factory.product.OssResource;
import cc.cloudcoding.resource.domain.service.factory.product.UploadResource;
import cc.cloudcoding.resource.domain.service.minio.config.MinioConfig;
import cc.cloudcoding.resource.infrastructure.repository.ResourceRepository;
import cc.cloudcoding.resource.model.po.ResourcePO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class MinioOssFactory implements AbstractOssFactory {


    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioConfig minioConfig;

    @Override
    public MinioResource uploadResource(UploadResource uploadResource) {
        try {
            minioClient.uploadObject(UploadObjectArgs.builder().bucket(uploadResource.getBucket()).object(uploadResource.getPath()).filename(uploadResource.getUploadResourceCommand().getUploadResourceAbsolutePath()).contentType(uploadResource.getUploadResourceCommand().getContentType()).build());

            MinioResource minioResource = new MinioResource();
            minioResource.setId(uploadResource.getUploadResourceCommand().getId());
            minioResource.setType(uploadResource.getUploadResourceCommand().getContentType());
            minioResource.setSize(uploadResource.getUploadResourceCommand().getSize());
            minioResource.setMd5(uploadResource.getUploadResourceCommand().getMd5());
            minioResource.setName(uploadResource.getUploadResourceCommand().getName());
            minioResource.setBucket(uploadResource.getBucket());
            log.info("上传文件到 minio 成功,bucket:{},path:{}", uploadResource.getBucket(), uploadResource.getUploadResourceCommand().getUploadResourceAbsolutePath());

            return minioResource;
        } catch (Exception e) {

            log.error("上传文件到 minio 出错,bucket:{},path:{},错误原因:{}", uploadResource.getBucket(), uploadResource.getUploadResourceCommand().getUploadResourceAbsolutePath(), e.getMessage(), e);
            CloudCodingException.cast("上传文件到文件系统失败");
        }
        return null;

    }

    @Override
    public String getEnterPoint(Long id) {
        ResourcePO resource = resourceRepository.getById(id);

        Map<String, String> reqParams = new HashMap<String, String>();
        reqParams.put("response-content-type", resource.getType());
        try {
            String url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(resource.getBucket()).object(resource.getPath()).expiry(2, TimeUnit.HOURS).extraQueryParams(reqParams).build());
            return url;

        } catch (Exception e) {
            log.error("获得资源访问链接出错 ,错误原因:{}", e.getMessage());
            CloudCodingException.cast("获得链接失败");
        }
        return null;
    }

    @Override
    public OssResource mergeResource(String md5, String path) {
        List<ResourcePO> list = resourceRepository.list(new LambdaQueryWrapper<ResourcePO>().eq(ResourcePO::getChunkMd5, md5));
        MinioResource minioResource = new MinioResource();
        minioResource.setSize(0L);


        List<ComposeSource> sourceObjectList = new ArrayList<ComposeSource>();
        for (ResourcePO p : list) {
            minioResource.setSize(minioResource.getSize() + p.getSize());
            minioResource.setType(p.getType());
            sourceObjectList.add(
                    ComposeSource.builder().bucket(p.getBucket()).object(p.getPath()).build());
        }
        try {
            minioClient.composeObject(
                    ComposeObjectArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .object(path)
                            .sources(sourceObjectList)
                            .build());
            minioResource.setName(path.substring(path.lastIndexOf("/")));
            minioResource.setPath(path);
            return minioResource;
        } catch (Exception e) {
            log.info("合并文件失败{} {}", md5, e.getMessage());
        }

        return null;
    }

    @Override
    public boolean removeResource(String id) {
        List<DeleteObject> removes = new LinkedList<>();
        ResourcePO po = resourceRepository.getById(id);
        List<ResourcePO> list = resourceRepository.list(new LambdaQueryWrapper<ResourcePO>().eq(ResourcePO::getChunkMd5, po.getChunkMd5()));
        for (ResourcePO d : list) {
            removes.add(new DeleteObject(d.getPath()));
        }

        Iterable<Result<DeleteError>> results =
                minioClient.removeObjects(
                        RemoveObjectsArgs.builder().bucket(minioConfig.getBucketName()).objects(removes).build());

        for (Result<DeleteError> result : results) {
            DeleteError error = null;
            try {
                error = result.get();
            } catch (Exception e) {

                log.info("删除文件异常 {}", e.getMessage());
                return false;
            }
        }
        return true;
    }
}
