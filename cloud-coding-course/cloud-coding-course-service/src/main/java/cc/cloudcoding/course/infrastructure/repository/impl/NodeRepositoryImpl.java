package cc.cloudcoding.course.infrastructure.repository.impl;

import cc.cloudcoding.course.infrastructure.mapper.NodeMapper;
import cc.cloudcoding.course.model.po.NodePO;
import cc.cloudcoding.course.infrastructure.repository.NodeRepository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author aichen
 * @since 2023-08-05 02:13:57
 */
@Service
public class NodeRepositoryImpl extends ServiceImpl<NodeMapper, NodePO> implements NodeRepository {

}
