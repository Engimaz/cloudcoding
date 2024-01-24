package cc.cloudcoding.comment.application.service;

import cc.cloudcoding.base.model.PageDTO;
import cc.cloudcoding.comment.model.command.AddCommentCommand;
import cc.cloudcoding.comment.model.dto.CommentDTO;
import cc.cloudcoding.comment.model.query.CommentQuery;

public interface ICommentApplication {
    PageDTO<CommentDTO, CommentQuery> list(CommentQuery query);

    boolean delete(String id);

    CommentDTO save(AddCommentCommand addCommentCommand);

    Long getCount(String id);
}
