package cn.edu.hcnu.manager.domain.event.url.remove;

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

public class RemoveUrlListener {

    @Autowired
    private FeatureUrlDomainService featureUrlDomainService;

    @EventListener
    public void handleCustomEvent(RemoveUrlEvent event) {
        featureUrlDomainService.removeByUrlId(event.getUrlId());
    }
}
