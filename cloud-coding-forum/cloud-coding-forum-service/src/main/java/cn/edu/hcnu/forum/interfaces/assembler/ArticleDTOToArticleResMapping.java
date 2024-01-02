package cn.edu.hcnu.forum.interfaces.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.forum.model.dto.ArticleDTO;
import cn.edu.hcnu.forum.model.res.ArticleRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleDTOToArticleResMapping extends IMapping<ArticleDTO, ArticleRes> {
}
