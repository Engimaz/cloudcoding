package cn.edu.hcnu.course.infrastructure.repository.impl;

import cn.edu.hcnu.course.infrastructure.mapper.NodeMapper;
import cn.edu.hcnu.course.infrastructure.repository.NodeRepository;
import cn.edu.hcnu.course.model.po.NodePO;
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
