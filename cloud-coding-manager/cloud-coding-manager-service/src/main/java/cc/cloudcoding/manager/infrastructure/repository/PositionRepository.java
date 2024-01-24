package cc.cloudcoding.manager.infrastructure.repository;

import cc.cloudcoding.manager.model.po.PositionPO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author aichen
 * @since 2023-08-05 02:13:57
 */
public interface PositionRepository extends IService<PositionPO> {

    /**
     *  删除一个社团下的所有职位
     * @param id
     * @return
     */
    List<PositionPO> removeByOrganizationId(Long id);
    /**
     *  删除一个社团下的所有职位
     * @param id
     * @return
     */
    List<PositionPO> queryByOrganizationId(Long id);
}
