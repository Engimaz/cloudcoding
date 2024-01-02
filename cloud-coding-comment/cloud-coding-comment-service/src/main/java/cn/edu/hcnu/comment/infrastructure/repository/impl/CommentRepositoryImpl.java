package cn.edu.hcnu.comment.infrastructure.repository.impl;

import cn.edu.hcnu.comment.infrastructure.mapper.CommentMapper;
import cn.edu.hcnu.comment.infrastructure.repository.CommentRepository;
import cn.edu.hcnu.comment.model.po.CommentPO;
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
