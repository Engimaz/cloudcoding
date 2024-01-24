package cc.cloudcoding.comment.model.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentQuery {
    private String parentId;
    private Integer size;
    private Integer page;
}
