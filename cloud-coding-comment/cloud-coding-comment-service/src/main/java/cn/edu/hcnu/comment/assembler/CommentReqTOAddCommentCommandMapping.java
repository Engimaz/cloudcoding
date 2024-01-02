package cn.edu.hcnu.comment.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.comment.model.command.AddCommentCommand;
import cn.edu.hcnu.comment.model.req.CommentReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentReqTOAddCommentCommandMapping extends IMapping<CommentReq, AddCommentCommand> {
}
