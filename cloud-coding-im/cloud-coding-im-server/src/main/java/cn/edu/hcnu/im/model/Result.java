package cn.edu.hcnu.im.model;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/6/23 14:22
 */
@Data
@AllArgsConstructor
public class Result {
    private String name;
    private String message;
    private LocalDateTime time;
    private String senderId;
    private String roomId;

    public Result(){
        this.time=LocalDateTime.now();
    }

    public static TextWebSocketFrame fail(String message) {
        Result result = new Result();
        result.setSenderId("-1");
        result.setMessage(message);
        return new TextWebSocketFrame(JSON.toJSONString(result));
    }

    public static TextWebSocketFrame success(String message) {
        Result result = new Result();
        result.setSenderId("-1");
        result.setMessage(message);
        return new TextWebSocketFrame(JSON.toJSONString(result));
    }

    public static TextWebSocketFrame success(Object message) {
        Result result = new Result();
        result.setMessage(JSON.toJSONString(message, JSONWriter.Feature.WriteNullStringAsEmpty));
        return new TextWebSocketFrame(JSON.toJSONString(result));
    }

    public  static TextWebSocketFrame  success(String senderId, String roomId, String content) {
        Result result = new Result();
        result.setSenderId(senderId);
        result.setRoomId(roomId);
        result.setMessage(content);
        return new TextWebSocketFrame(JSON.toJSONString(result));
    }
}
