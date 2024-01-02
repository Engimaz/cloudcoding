package cn.edu.hcnu.im.handler;

import cn.edu.hcnu.base.model.RestResponse;
import cn.edu.hcnu.comment.model.req.CommentReq;
import cn.edu.hcnu.comment.model.res.CommentRes;
import cn.edu.hcnu.comment.rpc.ICommentApi;
import cn.edu.hcnu.im.model.CommandEnum;
import cn.edu.hcnu.im.model.CommentMessage;
import cn.edu.hcnu.im.model.Result;
import cn.edu.hcnu.im.producer.IMessageProducer;
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
