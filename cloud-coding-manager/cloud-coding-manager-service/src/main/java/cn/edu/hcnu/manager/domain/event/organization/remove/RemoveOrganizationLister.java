package cn.edu.hcnu.manager.domain.event.organization.remove;

import cn.edu.hcnu.manager.infrastructure.repository.UserPositionRepository;
import cn.edu.hcnu.manager.model.po.PositionPO;
import cn.edu.hcnu.manager.infrastructure.repository.PositionRepository;
import cn.edu.hcnu.manager.model.po.UserPositionPO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/13 15:09
 */

@Component
public class RemoveOrganizationLister {
    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private UserPositionRepository userPositionRepository;

    @EventListener
    public void handleCustomEvent(RemoveOrganizationEvent event) {
        // 删除这个公司的所有的职位
        List<PositionPO> positions = positionRepository.removeByOrganizationId(event.getId());

        // 删除这个公司职位对应的用户
        userPositionRepository.remove(new LambdaQueryWrapper<UserPositionPO>().in(UserPositionPO::getPositionId, positions.stream().map(PositionPO::getId).collect(Collectors.toList())));
    }
}
