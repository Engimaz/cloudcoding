package cc.cloudcoding.forum.application.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.forum.domain.service.Article;
import cc.cloudcoding.forum.model.dto.ArticleDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleToArticleDTOMapping extends IMapping<Article, ArticleDTO> {
}
