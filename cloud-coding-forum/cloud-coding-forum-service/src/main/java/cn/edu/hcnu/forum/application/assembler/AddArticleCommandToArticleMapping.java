package cn.edu.hcnu.forum.application.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.forum.domain.service.Article;
import cn.edu.hcnu.forum.model.command.AddArticleCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddArticleCommandToArticleMapping extends IMapping<AddArticleCommand, Article> {
}
