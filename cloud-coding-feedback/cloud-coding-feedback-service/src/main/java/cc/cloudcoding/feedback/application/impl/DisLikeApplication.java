package cc.cloudcoding.feedback.application.impl;

import cc.cloudcoding.feedback.application.IDisLikeApplication;
import cc.cloudcoding.feedback.domain.service.DisLikeDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/29 2:18
 */
@Service
public class DisLikeApplication implements IDisLikeApplication {

    @Autowired
    private DisLikeDomainService disLikeDomainService;

    @Override
    public void addDisLike(String objectId, String userId) {
        disLikeDomainService.addDisLike(objectId, userId);
    }

    @Override
    public void removeDisLike(String objectId, String userId) {
        disLikeDomainService.removeDisLike(objectId, userId);
    }

    @Override
    public boolean isDisLikedByUser(String objectId, String userId) {
        return disLikeDomainService.isDisLikedByUser(objectId, userId);
    }

    @Override
    public Long countDisLike(String objectId) {
        return disLikeDomainService.countDisLike(objectId);
    }
}
