package cc.cloudcoding.auth.infrastructure.mapper;

import cc.cloudcoding.auth.model.po.AuthorizationPO;
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
public interface AuthorizationMapper extends BaseMapper<AuthorizationPO> {

}
