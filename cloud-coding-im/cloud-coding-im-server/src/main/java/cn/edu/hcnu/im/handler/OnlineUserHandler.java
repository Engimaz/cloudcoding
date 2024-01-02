package cn.edu.hcnu.im.handler;

import cn.edu.hcnu.im.model.Command;
import cn.edu.hcnu.im.model.CommandEnum;
import cn.edu.hcnu.im.model.OnlineUserMessage;
import cn.edu.hcnu.im.producer.IMessageProducer;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Set;


/**
 * @description:
 * @author: Administrator
 * @time: 2023/6/23 14:19
 */

@Component
public class OnlineUserHandler {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    IMessageProducer<OnlineUserMessage> messageProducer;

    // 绑定用户 与 channel 的关系
    public void execute(ChannelHandlerContext ctx, Command command) {

        String channelId = ctx.channel().id().asLongText();


        // 这里存储channel user 的关系
        redisTemplate.opsForValue().set("channel:id:" + channelId, command.getSenderId());

        // 保存用户与channel的关系
        redisTemplate.opsForValue().set("user:id:" + command.getSenderId(), channelId);


        // 记录用户在这个房间
        redisTemplate.boundSetOps("roomId:" + command.getRoomId()).add(command.getSenderId());
        redisTemplate.boundSetOps("online:user:" + command.getSenderId()).add(command.getRoomId());

        // 获得这个房间的所有id
        Set<String> members = redisTemplate.boundSetOps("roomId:" + command.getRoomId()).members();
        OnlineUserMessage joinRoomSuccessDTO = new OnlineUserMessage();
        joinRoomSuccessDTO.setCode(command.getCode());
        joinRoomSuccessDTO.setRoomId(command.getRoomId());
        joinRoomSuccessDTO.setSenderId(command.getSenderId());
        if (members != null) {
            joinRoomSuccessDTO.setUserIds(new ArrayList<>(members));
        }

        // 房间的人数
        joinRoomSuccessDTO.setCount(redisTemplate.boundSetOps("roomId:" + command.getRoomId()).size());
        joinRoomSuccessDTO.setContent("用户上线");
        // 登录连接 roomId:online
        messageProducer.sendMessage(CommandEnum.ONLINE.getCode(), joinRoomSuccessDTO);

    }
}
