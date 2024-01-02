package cn.edu.hcnu.manager.infrastructure.repository;

import cn.edu.hcnu.manager.model.po.PositionPO;
import cn.edu.hcnu.manager.model.po.UserPO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author aichen
 * @since 2023-08-05 02:13:57
 */
public interface UserRepository extends IService<UserPO> {

    /**
     * 根据昵称获取用户信息
     *
     * @param nickname 昵称
     * @return {@link UserPO}
     */
    UserPO getUserByNickname(String nickname);

    /**
     * 根据用户id获取用户的职位信息
     *
     * @param userId 用户id
     * @return {@link List}<{@link PositionPO}>
     */
    List<PositionPO> getPositionByUserId(Long userId);


}
