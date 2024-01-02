package cn.edu.hcnu.comment.application.service;

import cn.edu.hcnu.base.model.PageDTO;
import cn.edu.hcnu.comment.model.command.AddCommentCommand;
import cn.edu.hcnu.comment.model.dto.CommentDTO;
import cn.edu.hcnu.comment.model.query.CommentQuery;

public interface ICommentApplication {
    PageDTO<CommentDTO, CommentQuery> list(CommentQuery query);

    boolean delete(String id);

    CommentDTO save(AddCommentCommand addCommentCommand);

    Long getCount(String id);
}
