package cn.edu.hcnu.resource.application.service;

import cn.edu.hcnu.base.model.PageDTO;
import cn.edu.hcnu.resource.model.command.UploadResourceCommand;
import cn.edu.hcnu.resource.model.dto.ResourceDTO;
import cn.edu.hcnu.resource.model.query.ResourceQuery;

public interface IFileApplication {
    PageDTO<ResourceDTO, ResourceQuery> query(ResourceQuery resourceQuery);

    ResourceDTO uploadResource(UploadResourceCommand uploadResourceCommand);

    ResourceDTO checkResourceByMd5(String fileMd5);

    ResourceDTO mergeResource(String md5, String name, String userId, String type);

    String getEnterPoint(String id);

    boolean deleteResource(String id);
}
