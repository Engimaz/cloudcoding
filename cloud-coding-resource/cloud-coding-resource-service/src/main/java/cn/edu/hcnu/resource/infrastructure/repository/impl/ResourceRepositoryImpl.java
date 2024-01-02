package cn.edu.hcnu.resource.infrastructure.repository.impl;

import cn.edu.hcnu.resource.infrastructure.mapper.ResourceMapper;
import cn.edu.hcnu.resource.infrastructure.repository.ResourceRepository;
import cn.edu.hcnu.resource.model.po.ResourcePO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author aichen
 * @since 2023-08-05 02:13:57
 */
@Service
public class ResourceRepositoryImpl extends ServiceImpl<ResourceMapper, ResourcePO> implements ResourceRepository {

}
