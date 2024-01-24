package cc.cloudcoding.forum.interfaces.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.forum.model.dto.ArticleDTO;
import cc.cloudcoding.forum.model.res.ArticleRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleDTOToArticleResMapping extends IMapping<ArticleDTO, ArticleRes> {
}
