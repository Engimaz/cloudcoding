package cn.edu.hcnu.im.handler;

import cn.edu.hcnu.im.model.Command;
import cn.edu.hcnu.im.model.CommandEnum;
import cn.edu.hcnu.im.model.OnlineUserMessage;
import cn.edu.hcnu.im.model.Result;
import cn.edu.hcnu.im.producer.IMessageProducer;
import cn.edu.hcnu.im.session.SessionManager;
import com.alibaba.fastjson2.JSON;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/6/23 13:56
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    OnlineUserHandler onlineUserHandler;

    @Autowired
    private ChatHandler chatHandler;

    @Autowired
    private  CommentHandler commentHandler;

    @Autowired
    private IMessageProducer<OnlineUserMessage> iMessageProducer;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
        try {
            Command command = JSON.parseObject(frame.text(), Command.class);

            switch (CommandEnum.match(command.getCode())) {
                case ONLINE:
                    onlineUserHandler.execute(ctx, command);
                    break;
                case CHAT:
                    chatHandler.execute(ctx, frame);
                    break;
                case COMMMENT:
                    commentHandler.execute(ctx, frame);
                    break;
                default:
                    ctx.channel().writeAndFlush(Result.fail("不支持的code"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            ctx.channel().writeAndFlush(Result.fail(e.getMessage()));

        }
    }


    // 用户下线
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        String channelId = ctx.channel().id().asLongText();

        log.info("用户下线 channelId:{}", channelId);
        String userId = redisTemplate.opsForValue().get("channel:id:" + channelId);
        log.info("{}", userId);
        // 删除用户在线状态 注意这里存储了双向id
        if (!StringUtil.isNullOrEmpty(userId)) {
            log.info(userId);
            redisTemplate.delete("user:id:" + userId);
        }
        if (!StringUtil.isNullOrEmpty(channelId)) {
            redisTemplate.delete("channel:id:" + channelId);

        }


        // 获得用户所在的房间
        Set<String> roomIds = redisTemplate.boundSetOps("online:user:" + userId).members();

        // 移除用户的房间
        Optional.ofNullable(roomIds)
                .ifPresent(ids -> ids.forEach(roomId -> {
                    redisTemplate.boundSetOps("roomId:" + roomId).remove(userId);
                    Set<String> members = redisTemplate.boundSetOps("roomId:" + roomId).members();

                    OnlineUserMessage joinRoomSuccessDTO = new OnlineUserMessage();
                    joinRoomSuccessDTO.setCode(CommandEnum.ONLINE.getCode());
                    joinRoomSuccessDTO.setRoomId(roomId);
                    joinRoomSuccessDTO.setSenderId(userId);

                    joinRoomSuccessDTO.setUserIds(Optional.ofNullable(members)
                            .map(membersSet -> membersSet.stream().collect(Collectors.toList()))
                            .orElse(Collections.emptyList()));

                    joinRoomSuccessDTO.setCount(redisTemplate.boundSetOps("roomId:" + roomId).size());
                    joinRoomSuccessDTO.setContent("用户下线");
                    iMessageProducer.sendMessage("roomId:" + CommandEnum.ONLINE.getCode(), joinRoomSuccessDTO);
                }));

        redisTemplate.boundSetOps("online:user:" + userId).remove(userId);
    }

    // 用户上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        // 记录本机的channel
        SessionManager.getInstance().addChanel(ctx.channel());

    }
}
