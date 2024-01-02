package cn.edu.hcnu.auth.infrastructure.mapper;

import cn.edu.hcnu.auth.model.po.PositionPO;
import cn.edu.hcnu.auth.model.po.UserPO;
import cn.edu.hcnu.auth.model.po.UserPositionPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author aichen
 * @since 2023-08-05 02:13:57
 */
@Mapper
public interface UserPositionMapper extends BaseMapper<UserPositionPO> {

    /**
     * 根据位置id获取用户信息
     *
     * @param positionId 位置标识
     * @return {@link List}<{@link UserPO}>
     */
    List<UserPO> selectUserByPositionId(Long positionId);

    /**
     * 查询用户的职位信息
     *
     * @param userId 用户id
     * @return {@link List}<{@link PositionPO}>
     */
    List<PositionPO> selectPositionByUserId(@Param("userId") Long userId);
}
