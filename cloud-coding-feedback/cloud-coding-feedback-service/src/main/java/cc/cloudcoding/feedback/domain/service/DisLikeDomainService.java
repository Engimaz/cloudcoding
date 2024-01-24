package cc.cloudcoding.feedback.domain.service;

import cc.cloudcoding.feedback.infrastructure.repository.DislikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/29 1:59
 */

@Component
public class DisLikeDomainService {
    @Autowired
    private DislikeRepository dislikeRepository;

    public void addDisLike(String objectId, String userId) {
        dislikeRepository.addDisLike(objectId, userId);
    }

    public void removeDisLike(String objectId, String userId) {
        dislikeRepository.removeDisLike(objectId, userId);
    }

    public boolean isDisLikedByUser(String objectId, String userId) {
        return dislikeRepository.isDisLikedByUser(objectId, userId);
    }

    public Long countDisLike(String objectId) {
        return dislikeRepository.countDisLike(objectId);
    }
}
