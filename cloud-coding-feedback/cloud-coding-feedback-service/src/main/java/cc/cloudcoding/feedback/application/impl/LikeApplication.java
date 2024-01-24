package cc.cloudcoding.feedback.application.impl;

import cc.cloudcoding.feedback.application.ILikeApplication;
import cc.cloudcoding.feedback.domain.service.LikeDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/29 1:55
 */

@Service
public class LikeApplication implements ILikeApplication {

    @Autowired
    private LikeDomainService likeDomainService;

    public void addLike(String objectId, String userId) {
        likeDomainService.addLike(objectId, userId);
    }

    public void removeLike(String objectId, String userId) {
        likeDomainService.removeLike(objectId, userId);
    }

    public boolean isLikedByUser(String objectId, String userId) {
        return likeDomainService.isLikedByUser(objectId, userId);
    }

    public Long countLike(String objectId) {
        return likeDomainService.countLike(objectId);
    }
}
