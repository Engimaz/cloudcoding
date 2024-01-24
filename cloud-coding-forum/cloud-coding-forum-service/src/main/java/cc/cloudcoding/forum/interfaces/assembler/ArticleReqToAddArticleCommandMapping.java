package cc.cloudcoding.forum.interfaces.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.forum.model.command.AddArticleCommand;
import cc.cloudcoding.forum.model.req.ArticleReq;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ArticleReqToAddArticleCommandMapping extends IMapping<ArticleReq, AddArticleCommand> {
}
