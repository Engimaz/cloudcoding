package cc.cloudcoding.comment.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.comment.model.command.AddCommentCommand;
import cc.cloudcoding.comment.model.req.CommentReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentReqTOAddCommentCommandMapping extends IMapping<CommentReq, AddCommentCommand> {
}
