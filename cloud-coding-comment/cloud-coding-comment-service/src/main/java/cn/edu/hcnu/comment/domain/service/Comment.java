package cn.edu.hcnu.comment.domain.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private String id;
    private String replyId;
    private String parentId;
    private String userId;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
