package cn.edu.hcnu.feedback.infrastructure.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/29 1:57
 */
@Repository
public class DislikeRepository {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private static final String HASH_KEY = "dislikes"; // 用于存储点赞信息的 Redis 哈希表 Key

    public void addDisLike(String objectId, String userId) {
        redisTemplate.opsForHash().put(HASH_KEY + ":" + objectId, userId, true);
    }

    public void removeDisLike(String objectId, String userId) {
        redisTemplate.opsForHash().delete(HASH_KEY + ":" + objectId, userId);
    }

    public boolean isDisLikedByUser(String objectId, String userId) {
        return redisTemplate.opsForHash().hasKey(HASH_KEY + ":" + objectId, userId);
    }

    public Long countDisLike(String objectId) {
        return redisTemplate.opsForHash().size(HASH_KEY + ":" + objectId);
    }
}
