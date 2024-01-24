package cc.cloudcoding.forum.application.assembler;


import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.forum.domain.service.Article;
import cc.cloudcoding.forum.model.command.AddArticleCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddArticleCommandToArticleMapping extends IMapping<AddArticleCommand, Article> {
}
