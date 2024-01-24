package cc.cloudcoding.forum.interfaces.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cn.edu.hcnu.forum.model.command.AddArticleCommand;
import cn.edu.hcnu.forum.model.req.ArticleReq;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ArticleReqToAddArticleCommandMapping extends IMapping<ArticleReq, AddArticleCommand> {
}
