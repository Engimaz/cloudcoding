package cc.cloudcoding.forum.interfaces.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cn.edu.hcnu.forum.model.dto.ArticleDTO;
import cn.edu.hcnu.forum.model.res.ArticleRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleDTOToArticleResMapping extends IMapping<ArticleDTO, ArticleRes> {
}
