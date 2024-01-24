package cc.cloudcoding.resource.domain.service.resource.factory;

import cc.cloudcoding.resource.model.po.ResourcePO;
import cc.cloudcoding.resource.domain.service.resource.Resource;
import org.springframework.stereotype.Component;

@Component
public class ResourceFactory {

    public Resource createResource(ResourcePO po) {
        if (po==null) return null;
        Resource resource = new Resource();
        resource.setId(po.getId());
        resource.setPath(po.getPath());
        resource.setState(po.getState());
        resource.setHost(po.getHost());
        resource.setBucket(po.getBucket());
        resource.setType(po.getType());
        resource.setName(po.getName());
        resource.setSize(po.getSize());
        resource.setCreateTime(po.getCreateTime());
        resource.setChunkMd5(po.getChunkMd5());
        return resource;
    }

    public ResourcePO createResourcePO(Resource resource) {
        ResourcePO po = new ResourcePO();
        po.setUserId(resource.getUserId());
        po.setMd5(resource.getMd5());
        po.setOrder(resource.getOrder());
        po.setPath(resource.getPath());
        po.setState(resource.getState());
        po.setHost(resource.getHost());
        po.setBucket(resource.getBucket());
        po.setType(resource.getType());
        po.setName(resource.getName());
        po.setSize(resource.getSize());
        po.setChunkMd5(resource.getChunkMd5());
        return po;
    }
}
