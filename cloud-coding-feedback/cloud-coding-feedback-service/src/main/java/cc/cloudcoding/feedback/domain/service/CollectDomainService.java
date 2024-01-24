package cc.cloudcoding.feedback.domain.service;

import cc.cloudcoding.feedback.infrastructure.repository.CollectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/29 2:00
 */
@Component
public class CollectDomainService {
    @Autowired
    private CollectRepository collectRepository;

    public void addCollect(String objectId, String userId) {
        collectRepository.addCollect(objectId, userId);
    }

    public void removeCollect(String objectId, String userId) {
        collectRepository.removeCollect(objectId, userId);
    }

    public boolean isCollectByUser(String objectId, String userId) {
        return collectRepository.isCollectByUser(objectId, userId);
    }

    public Long countCollect(String objectId) {
        return collectRepository.countCollect(objectId);
    }
}
