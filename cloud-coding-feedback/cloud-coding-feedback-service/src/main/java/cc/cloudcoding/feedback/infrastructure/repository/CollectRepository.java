package cc.cloudcoding.feedback.infrastructure.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/29 1:57
 */
@Repository
public class CollectRepository {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    // 用于存储收藏信息的 Redis 哈希表 Key
    private static final String HASH_KEY = "collect";

    public void addCollect(String objectId, String userId) {
        redisTemplate.opsForHash().put(HASH_KEY + ":" + objectId, userId, true);
    }

    public void removeCollect(String objectId, String userId) {
        redisTemplate.opsForHash().delete(HASH_KEY + ":" + objectId, userId);
    }

    public boolean isCollectByUser(String objectId, String userId) {
        return redisTemplate.opsForHash().hasKey(HASH_KEY + ":" + objectId, userId);
    }

    public Long countCollect(String objectId) {
        return redisTemplate.opsForHash().size(HASH_KEY + ":" + objectId);
    }
}
