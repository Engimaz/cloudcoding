package cn.edu.hcnu.manager.infrastructure.repository;

import cn.edu.hcnu.manager.domain.service.relation.UserPosition;
import cn.edu.hcnu.manager.model.po.OrganizationPO;
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
public interface OrganizationRepository extends IService<OrganizationPO> {

    List<OrganizationPO> findByUserId(Long userId);


}
