package cc.cloudcoding.comment.application.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.comment.domain.service.Comment;
import cc.cloudcoding.comment.model.command.AddCommentCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddCommentCommandToCommentMapping extends IMapping<AddCommentCommand, Comment> {
}
