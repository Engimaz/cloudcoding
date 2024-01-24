package cc.cloudcoding.forum.application.service;

import cc.cloudcoding.base.model.CommonQuery;
import cc.cloudcoding.base.model.PageDTO;
import cc.cloudcoding.forum.model.command.AddArticleCommand;
import cc.cloudcoding.forum.model.command.UpdateArticleCommand;
import cc.cloudcoding.forum.model.dto.ArticleDTO;

public interface IArticleApplication {
    ArticleDTO save(AddArticleCommand addArticleCommand);

    ArticleDTO update(UpdateArticleCommand addArticleCommand);

    PageDTO<ArticleDTO, CommonQuery> listByStatus(CommonQuery commonQuery, String status);

    ArticleDTO get(Long id);

    boolean delete(Long id);

    PageDTO<ArticleDTO, CommonQuery> list(CommonQuery commonQuery, String userId);
}
