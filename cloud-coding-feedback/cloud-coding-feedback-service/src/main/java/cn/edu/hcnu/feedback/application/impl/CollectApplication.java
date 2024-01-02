package cn.edu.hcnu.feedback.application.impl;

import cn.edu.hcnu.feedback.application.ICollectApplication;
import cn.edu.hcnu.feedback.domain.service.CollectDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/29 2:20
 */
@Service
public class CollectApplication implements ICollectApplication {

    @Autowired
    private CollectDomainService collectDomainService;

    @Override
    public void addCollect(String objectId, String userId) {
        collectDomainService.addCollect(objectId, userId);
    }

    @Override
    public void removeCollect(String objectId, String userId) {
        collectDomainService.removeCollect(objectId, userId);
    }

    @Override
    public boolean isCollectdByUser(String objectId, String userId) {
        return collectDomainService.isCollectByUser(objectId, userId);
    }

    @Override
    public Long countCollect(String objectId) {
        return collectDomainService.countCollect(objectId);
    }
}
