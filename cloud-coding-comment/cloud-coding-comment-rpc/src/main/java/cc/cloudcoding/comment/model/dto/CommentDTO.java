package cc.cloudcoding.comment.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private String id;
    private String replyId;
    private String parentId;
    private String userId;
    private String content;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
