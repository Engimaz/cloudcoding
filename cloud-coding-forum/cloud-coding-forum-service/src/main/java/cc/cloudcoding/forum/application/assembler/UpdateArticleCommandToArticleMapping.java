package cc.cloudcoding.forum.application.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cn.edu.hcnu.forum.domain.service.Article;
import cn.edu.hcnu.forum.model.command.UpdateArticleCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateArticleCommandToArticleMapping extends IMapping<UpdateArticleCommand, Article> {
}
