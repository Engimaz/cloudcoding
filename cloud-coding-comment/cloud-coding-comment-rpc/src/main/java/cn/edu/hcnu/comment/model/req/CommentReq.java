package cn.edu.hcnu.comment.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentReq implements Serializable {
    private String id;
    private String replyId;
    private String parentId;
    private String userId;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
