package cc.cloudcoding.comment.infrastructure.mapper;

import cc.cloudcoding.comment.model.po.CommentPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author aichen
 * @since 2023-08-05 02:13:57
 */
@Mapper
public interface CommentMapper extends BaseMapper<CommentPO> {

    Long getCount(String id);


}
