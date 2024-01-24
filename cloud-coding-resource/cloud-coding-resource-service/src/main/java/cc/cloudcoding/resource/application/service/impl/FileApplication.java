package cc.cloudcoding.resource.application.service.impl;

import cc.cloudcoding.base.model.PageDTO;
import cc.cloudcoding.id.domain.service.IDGenerator;
import cc.cloudcoding.resource.application.assembler.ResourceToResourceDTOMapping;
import cc.cloudcoding.resource.application.service.IFileApplication;
import cc.cloudcoding.resource.domain.service.factory.AbstractOssFactory;
import cc.cloudcoding.resource.domain.service.factory.product.OssResource;
import cc.cloudcoding.resource.domain.service.factory.product.UploadResource;
import cc.cloudcoding.resource.domain.service.minio.config.MinioConfig;
import cc.cloudcoding.resource.domain.service.resource.Resource;
import cc.cloudcoding.resource.domain.service.resource.ResourceDomainService;
import cc.cloudcoding.resource.model.command.UploadResourceCommand;
import cc.cloudcoding.resource.model.dto.ResourceDTO;
import cc.cloudcoding.resource.model.query.ResourceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class FileApplication implements IFileApplication {


    @Autowired
    private AbstractOssFactory factory;

    @Autowired
    private ResourceDomainService resourceDomainService;

    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    @Qualifier("snowflake")
    private IDGenerator idGenerator;

    @Autowired
    private ResourceToResourceDTOMapping resourceToResourceDTOMapping;

    @Override
    public PageDTO<ResourceDTO, ResourceQuery> query(ResourceQuery resourceQuery) {

        List<Resource> list = resourceDomainService.list(resourceQuery);
        Long count = resourceDomainService.count(resourceQuery);
        return new PageDTO<>(resourceToResourceDTOMapping.sourceToTarget(list), count, resourceQuery);
    }

    @Override
    public ResourceDTO uploadResource(UploadResourceCommand uploadResourceCommand) {


        uploadResourceCommand.setId(idGenerator.nextID());

        UploadResource uploadResource = new UploadResource();
        uploadResource.setUploadResourceCommand(uploadResourceCommand);
        uploadResource.setUserId(uploadResourceCommand.getUserId());
        uploadResource.setBucket(minioConfig.getBucketName());
        uploadResource.setPath(generatePath() + uploadResourceCommand.getName());

        OssResource ossResource = factory.uploadResource(uploadResource);

        if (ossResource != null) {
            Resource resource = new Resource();

            resource.setId(uploadResourceCommand.getId());
            resource.setName(uploadResourceCommand.getName());
            resource.setHost(minioConfig.getEndpoint());

            resource.setBucket(uploadResource.getBucket());
            resource.setMd5(uploadResourceCommand.getMd5());
            resource.setOrder(1);

            resource.setSize(uploadResourceCommand.getSize());
            resource.setType(uploadResourceCommand.getContentType());
            resource.setPath(uploadResource.getPath());

            resource.setState(1);
            resource.setUserId(Long.valueOf(uploadResourceCommand.getUserId()));
            resource.setChunkMd5(uploadResourceCommand.getChunkMd5());

            Resource save = resourceDomainService.save(resource);
            return resourceToResourceDTOMapping.sourceToTarget(save);
        }
        return null;


    }

    @Override
    public ResourceDTO checkResourceByMd5(String fileMd5) {

        Resource r = resourceDomainService.queryByMd5(fileMd5);

        return resourceToResourceDTOMapping.sourceToTarget(r);

    }

    @Override
    public ResourceDTO mergeResource(String md5, String name, String userId, String type) {

        OssResource ossResource = factory.mergeResource(md5, generatePath() + name);


        if (ossResource != null) {
            Resource resource = new Resource();
            resource.setId(idGenerator.nextID());
            resource.setMd5(md5);
            resource.setChunkMd5(md5);
            resource.setState(1);
            resource.setSize(ossResource.getSize());
            resource.setType(type);
            resource.setOrder(1);
            resource.setBucket(minioConfig.getBucketName());
            resource.setPath(ossResource.getPath());
            resource.setName(ossResource.getName());
            resource.setUserId(Long.valueOf(userId));
            Resource save = resourceDomainService.save(resource);
            return resourceToResourceDTOMapping.sourceToTarget(save);
        }
        return null;
    }

    private String generatePath() {

        // 格式化 LocalDateTime 为字符串
        String formattedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 将短横线替换为斜杠
        String finalFormattedDate = formattedDate.replace("-", "/");

        return "/" + finalFormattedDate + "/";
    }

    @Override
    public String getEnterPoint(String id) {
        String enterPoint = factory.getEnterPoint(Long.valueOf(id));
        return enterPoint;
    }

    @Override
    public boolean deleteResource(String id) {

        boolean b = factory.removeResource(id);
        if (!b) return b;

        return false;
    }
}
