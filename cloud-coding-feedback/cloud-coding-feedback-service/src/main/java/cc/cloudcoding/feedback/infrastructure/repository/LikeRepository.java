package cc.cloudcoding.feedback.infrastructure.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/29 1:56
 */
@Repository
public class LikeRepository {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private static final String HASH_KEY = "Likes"; // 用于存储点赞信息的 Redis 哈希表 Key

    public void addLike(String objectId, String userId) {
        redisTemplate.opsForHash().put(HASH_KEY + ":" + objectId, userId, true);
    }

    public void removeLike(String objectId, String userId) {
        redisTemplate.opsForHash().delete(HASH_KEY + ":" + objectId, userId);
    }

    public boolean isLikedByUser(String objectId, String userId) {
        return redisTemplate.opsForHash().hasKey(HASH_KEY + ":" + objectId, userId);
    }

    public Long countLike(String objectId) {
        return redisTemplate.opsForHash().size(HASH_KEY + ":" + objectId);
    }
}
