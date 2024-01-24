package cc.cloudcoding.forum.infrastructure.repository.impl;


import cn.edu.hcnu.forum.infrastructure.mapper.TopicMapper;
import cn.edu.hcnu.forum.infrastructure.repository.TopicRepository;
import cn.edu.hcnu.forum.model.po.TopicPO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author aichen
 * @since 2023-10-15 07:28:12
 */
@Service
public class TopicRepositoryImpl extends ServiceImpl<TopicMapper, TopicPO> implements TopicRepository {

}
