package cc.cloudcoding.forum.application.service;

import cc.cloudcoding.base.model.CommonQuery;
import cc.cloudcoding.base.model.PageDTO;
import cn.edu.hcnu.forum.model.command.AddArticleCommand;
import cn.edu.hcnu.forum.model.command.UpdateArticleCommand;
import cn.edu.hcnu.forum.model.dto.ArticleDTO;

public interface IArticleApplication {
    ArticleDTO save(AddArticleCommand addArticleCommand);

    ArticleDTO update(UpdateArticleCommand addArticleCommand);

    PageDTO<ArticleDTO, CommonQuery> listByStatus(CommonQuery commonQuery, String status);

    ArticleDTO get(Long id);

    boolean delete(Long id);

    PageDTO<ArticleDTO, CommonQuery> list(CommonQuery commonQuery, String userId);
}
