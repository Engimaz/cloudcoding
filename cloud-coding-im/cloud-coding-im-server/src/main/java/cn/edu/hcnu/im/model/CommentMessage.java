package cn.edu.hcnu.im.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/28 14:41
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentMessage extends Command implements Serializable {
    private String id;
    private String replyId;
    // 楼层id
    private String parentId;
    // 回复id
    private String content;

}
