package cn.edu.hcnu.resource.domain.service.resource;

import cn.edu.hcnu.resource.domain.service.minio.config.MinioConfig;
import cn.edu.hcnu.resource.domain.service.resource.factory.ResourceFactory;
import cn.edu.hcnu.resource.infrastructure.repository.ResourceRepository;
import cn.edu.hcnu.resource.model.po.ResourcePO;
import cn.edu.hcnu.resource.model.query.ResourceQuery;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ResourceDomainService {


    @Autowired
    private MinioConfig config;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ResourceFactory resourceFactory;

    public Resource save(Resource resource) {
        ResourcePO po = resourceFactory.createResourcePO(resource);
        boolean save = resourceRepository.save(po);
        if (save) {
            return resourceFactory.createResource(po);
        }
        return null;
    }


    public Resource queryByMd5(String fileMd5) {
        ResourcePO one = resourceRepository.getOne(new LambdaQueryWrapper<ResourcePO>().eq(ResourcePO::getMd5, fileMd5));
        return resourceFactory.createResource(one);
    }

    public List<Resource> list(ResourceQuery resourceQuery) {
        return this.pageQuery(resourceQuery).getRecords()
                .stream()
                .map(f -> resourceFactory.createResource(f)).collect(Collectors.toList());
    }

    public Long count(ResourceQuery resourceQuery) {
        return this.pageQuery(resourceQuery).getTotal();
    }


    private Page<ResourcePO> pageQuery(ResourceQuery resourceQuery) {
        Page<ResourcePO> p1 = new Page<>(resourceQuery.getPage(), resourceQuery.getSize());
        LambdaQueryWrapper<ResourcePO> wrapper = new LambdaQueryWrapper<ResourcePO>().eq(resourceQuery.getUserId() != null, ResourcePO::getUserId, resourceQuery.getUserId())
                .like(resourceQuery.getName() != null, ResourcePO::getName, resourceQuery.getName())
                .eq(resourceQuery.getType() != null, ResourcePO::getType, resourceQuery.getType())
                .eq(resourceQuery.getState() != null, ResourcePO::getState, resourceQuery.getState());
        return resourceRepository.getBaseMapper().selectPage(p1, wrapper);
    }
}
