package cn.edu.hcnu.forum.interfaces.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.forum.model.command.AddArticleCommand;
import cn.edu.hcnu.forum.model.req.ArticleReq;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ArticleReqToAddArticleCommandMapping extends IMapping<ArticleReq, AddArticleCommand> {
}
