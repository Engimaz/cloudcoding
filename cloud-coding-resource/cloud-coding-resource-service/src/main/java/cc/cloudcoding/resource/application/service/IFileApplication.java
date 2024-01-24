package cc.cloudcoding.resource.application.service;

import cc.cloudcoding.base.model.PageDTO;
import cc.cloudcoding.resource.model.command.UploadResourceCommand;
import cc.cloudcoding.resource.model.dto.ResourceDTO;
import cc.cloudcoding.resource.model.query.ResourceQuery;

public interface IFileApplication {
    PageDTO<ResourceDTO, ResourceQuery> query(ResourceQuery resourceQuery);

    ResourceDTO uploadResource(UploadResourceCommand uploadResourceCommand);

    ResourceDTO checkResourceByMd5(String fileMd5);

    ResourceDTO mergeResource(String md5, String name, String userId, String type);

    String getEnterPoint(String id);

    boolean deleteResource(String id);
}
