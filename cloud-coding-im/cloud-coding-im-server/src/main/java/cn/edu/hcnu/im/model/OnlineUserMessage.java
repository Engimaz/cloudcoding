package cn.edu.hcnu.im.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlineUserMessage extends Command implements Serializable {
    // 在线人数
    private Long count;
    // 消息提示
    private String content;
    // 用户id
    private List<String> userIds;
}
