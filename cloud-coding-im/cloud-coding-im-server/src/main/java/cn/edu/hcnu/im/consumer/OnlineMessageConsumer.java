package cn.edu.hcnu.im.consumer;

import cn.edu.hcnu.im.model.OnlineUserMessage;
import cn.edu.hcnu.im.model.Result;
import cn.edu.hcnu.im.session.SessionManager;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/28 12:22
 */

@Component
@RocketMQMessageListener(
        consumerGroup = "cloud-coding-websocket-online",
        topic = "online"
)
@Slf4j
public class OnlineMessageConsumer implements RocketMQListener<OnlineUserMessage> {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void onMessage(OnlineUserMessage s) {

        log.info("正常处理消息,{}", s);

        // 获得用户的房间号
        String roomId = s.getRoomId();
        Optional.ofNullable(redisTemplate.boundSetOps("roomId:" + roomId).members()).ifPresent(userids -> {
            userids.stream().forEach(id -> {
                // 通过用户id 拿回他的channel
                String channelId = redisTemplate.opsForValue().get("user:id:" + id);
                // 获得用户channel
                Optional.ofNullable(SessionManager.getInstance().getChannel(channelId)).ifPresent(channel -> {
                    log.info("正在通知用户 {} 该用户 {} 变动 {}", id, s.getSenderId(),s.getContent());
                    // 通知前端在线人数
                    channel.writeAndFlush(Result.success(s));
                });
            });
        });
    }
}
