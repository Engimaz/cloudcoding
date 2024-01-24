package cc.cloudcoding.comment.model.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCommentCommand {
    private String replyId;
    private String parentId;
    private String userId;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
