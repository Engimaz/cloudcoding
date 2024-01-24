package cc.cloudcoding.comment.infrastructure.repository.impl;

import cc.cloudcoding.comment.infrastructure.mapper.CommentMapper;
import cc.cloudcoding.comment.infrastructure.repository.CommentRepository;
import cc.cloudcoding.comment.model.po.CommentPO;
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
public class CommentRepositoryImpl extends ServiceImpl<CommentMapper, CommentPO> implements CommentRepository {

}
