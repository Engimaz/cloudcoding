package cn.edu.hcnu.im.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/6/23 14:11
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Command {
    // 消息类型编码
    private String code;
    // 房间号
    private String roomId;
    // 就是userId
    private String senderId;
    // 消息生产时间
    private LocalDateTime createTime;
}
