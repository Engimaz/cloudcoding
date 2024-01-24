package cc.cloudcoding.feedback.domain.service;

import cc.cloudcoding.feedback.infrastructure.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/29 1:59
 */

@Component
public class LikeDomainService {

    @Autowired
    private LikeRepository likeRepository;

    public void addLike(String objectId, String userId) {
        likeRepository.addLike(objectId, userId);
    }

    public void removeLike(String objectId, String userId) {
        likeRepository.removeLike(objectId, userId);
    }

    public boolean isLikedByUser(String objectId, String userId) {
      return   likeRepository.isLikedByUser(objectId, userId);
    }

    public Long countLike(String objectId) {
        return likeRepository.countLike(objectId);
    }
}
