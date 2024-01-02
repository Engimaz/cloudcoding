package cn.edu.hcnu.forum.interfaces.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.forum.model.command.UpdateArticleCommand;
import cn.edu.hcnu.forum.model.req.ArticleReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleReqToUpdateArticleCommandMapping extends IMapping<ArticleReq, UpdateArticleCommand> {
}