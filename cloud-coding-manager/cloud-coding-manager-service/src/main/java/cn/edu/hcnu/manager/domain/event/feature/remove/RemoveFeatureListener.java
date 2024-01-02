package cn.edu.hcnu.manager.domain.event.feature.remove;

import cn.edu.hcnu.manager.domain.service.relation.FeatureOrganizationDomainService;
import cn.edu.hcnu.manager.domain.service.relation.FeatureUrlDomainService;
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
    private FeatureUrlDomainService featureUrlDomainService;

    @Autowired
    private FeatureOrganizationDomainService featureOrganizationDomainService;

    @EventListener
    public void handleCustomEvent(RemoveFeatureEvent event) {
        // 移除与之相关的url
        featureUrlDomainService.removeByFeatureId(event.getFeatureId());
        // 在对应的组织移除
        featureOrganizationDomainService.removeByFeatureId(event.getFeatureId());
    }
}
