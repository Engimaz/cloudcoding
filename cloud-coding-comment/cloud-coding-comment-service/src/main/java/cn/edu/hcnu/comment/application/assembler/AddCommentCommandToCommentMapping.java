package cn.edu.hcnu.comment.application.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.comment.domain.service.Comment;
import cn.edu.hcnu.comment.model.command.AddCommentCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddCommentCommandToCommentMapping extends IMapping<AddCommentCommand, Comment> {
}
