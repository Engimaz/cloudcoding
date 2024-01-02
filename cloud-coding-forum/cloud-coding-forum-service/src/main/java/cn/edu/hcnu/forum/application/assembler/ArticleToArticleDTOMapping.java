package cn.edu.hcnu.forum.application.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.forum.domain.service.Article;
import cn.edu.hcnu.forum.model.dto.ArticleDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleToArticleDTOMapping extends IMapping<Article, ArticleDTO> {
}
