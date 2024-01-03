package cn.edu.hcnu.manager.domain.event.feature.remove;

import cn.edu.hcnu.manager.infrastructure.repository.FeatureOrganizationRepository;
import cn.edu.hcnu.manager.infrastructure.repository.FeatureUrlRepository;
import cn.edu.hcnu.manager.model.po.FeatureOrganizationPO;
import cn.edu.hcnu.manager.model.po.FeatureUrlPO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 13:46
 */
@Service

public class RemoveFeatureListener {

    @Autowired
    private FeatureUrlRepository featureUrlRepository;

    @Autowired
    private FeatureOrganizationRepository featureOrganizationRepository;

    @EventListener
    public void handleCustomEvent(RemoveFeatureEvent event) {
        // 移除与之相关的url
        featureUrlRepository.remove(new LambdaQueryWrapper<FeatureUrlPO>().eq(FeatureUrlPO::getFeatureId, event.getFeatureId()));
        // 在对应的组织移除
        featureOrganizationRepository.remove(new LambdaQueryWrapper<FeatureOrganizationPO>().eq(FeatureOrganizationPO::getFeatureId, event.getFeatureId()));
    }
}
