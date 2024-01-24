package cc.cloudcoding.im.handler;

import cc.cloudcoding.im.producer.IMessageProducer;
import cc.cloudcoding.im.model.MessageDTO;
import cc.cloudcoding.im.model.Result;
import com.alibaba.fastjson2.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/6/23 15:21
 */
@Service
public class ChatHandler {


    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    IMessageProducer<MessageDTO> rocketMqMessageProducer;


    public void execute(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
        try {
            MessageDTO messageDTO = JSON.parseObject(frame.text(), MessageDTO.class);
            if (StringUtil.isNullOrEmpty(messageDTO.getRoomId())) {
                ctx.channel().writeAndFlush(Result.fail("发送的房间号不存在"));
            }
            // 提取房间号 将消息发送到 房间号——>消息体
            rocketMqMessageProducer.sendMessage(messageDTO.getRoomId(), messageDTO);

        } catch (Exception e) {
            ctx.channel().writeAndFlush(Result.fail("消息格式不对"));
        }
    }
}
