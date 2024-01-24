package cc.cloudcoding.im.handler;

import cc.cloudcoding.base.model.RestResponse;
import cc.cloudcoding.comment.model.req.CommentReq;
import cc.cloudcoding.comment.model.res.CommentRes;
import cc.cloudcoding.comment.rpc.ICommentApi;
import cc.cloudcoding.im.producer.IMessageProducer;
import cc.cloudcoding.im.model.CommandEnum;
import cc.cloudcoding.im.model.CommentMessage;
import cc.cloudcoding.im.model.Result;
import com.alibaba.fastjson2.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/28 12:06
 */
@Service
@Slf4j
public class CommentHandler {


    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    IMessageProducer<CommentMessage> rocketMqMessageProducer;

    @DubboReference(group = "comment")
    private ICommentApi iCommentApi;


    public void execute(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
        try {
            CommentMessage messageDTO = JSON.parseObject(frame.text(), CommentMessage.class);
            messageDTO.setCreateTime(LocalDateTime.now());
            if (StringUtil.isNullOrEmpty(messageDTO.getRoomId())) {
                ctx.channel().writeAndFlush(Result.fail("发送的房间号不存在"));
            }
            // 远程调用保存信息
            CommentReq commentReq = new CommentReq();
            commentReq.setContent(messageDTO.getContent());
            commentReq.setReplyId(messageDTO.getReplyId());
            commentReq.setParentId(messageDTO.getParentId());
            commentReq.setUserId(messageDTO.getSenderId());
            RestResponse<CommentRes> restResponse = iCommentApi.addComment(commentReq);
            messageDTO.setId(restResponse.getResult().getId());
            // 提取房间号 将消息发送到 房间号——>消息体
            rocketMqMessageProducer.sendMessage(CommandEnum.COMMMENT.getCode(), messageDTO);
            log.info("消息已经推送到websocker {}", messageDTO);
        } catch (Exception e) {
            ctx.channel().writeAndFlush(Result.fail("消息格式不对"));
        }
    }
}
