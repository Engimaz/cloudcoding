package cc.cloudcoding.im.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/6/23 14:46
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageDTO extends Command{

    // 发送内容
    private String content;
    // 发送时间
    private LocalDateTime time;

}
