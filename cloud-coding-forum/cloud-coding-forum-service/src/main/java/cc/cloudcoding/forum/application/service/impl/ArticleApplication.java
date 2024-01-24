package cc.cloudcoding.forum.application.service.impl;

import cc.cloudcoding.base.model.CommonQuery;
import cc.cloudcoding.base.model.PageDTO;
import cc.cloudcoding.forum.application.assembler.AddArticleCommandToArticleMapping;
import cc.cloudcoding.forum.application.assembler.ArticleToArticleDTOMapping;
import cc.cloudcoding.forum.application.assembler.UpdateArticleCommandToArticleMapping;
import cc.cloudcoding.forum.application.service.IArticleApplication;
import cc.cloudcoding.forum.domain.service.Article;
import cc.cloudcoding.forum.domain.service.ArticleDomainService;
import cc.cloudcoding.forum.model.command.AddArticleCommand;
import cc.cloudcoding.forum.model.command.UpdateArticleCommand;
import cc.cloudcoding.forum.model.dto.ArticleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/15 20:05
 */
@Service
public class ArticleApplication implements IArticleApplication {

    @Autowired
    private ArticleDomainService articleDomainService;

    @Autowired
    private AddArticleCommandToArticleMapping articleCommandToArticleMapping;

    @Autowired
    private ArticleToArticleDTOMapping articleToArticleDTOMapping;

    @Autowired
    private UpdateArticleCommandToArticleMapping updateArticleCommandToArticleMapping;

    @Override
    public ArticleDTO save(AddArticleCommand addArticleCommand) {
        Article article = articleCommandToArticleMapping.sourceToTarget(addArticleCommand);
        Article article1 = articleDomainService.save(article);
        return articleToArticleDTOMapping.sourceToTarget(article1);
    }

    @Override
    public ArticleDTO update(UpdateArticleCommand updateArticleCommand) {
        Article article = updateArticleCommandToArticleMapping.sourceToTarget(updateArticleCommand);
        Article article1 = articleDomainService.update(article);
        return articleToArticleDTOMapping.sourceToTarget(article1);
    }

    @Override
    public PageDTO<ArticleDTO, CommonQuery> listByStatus(CommonQuery commonQuery,String status) {
        List<Article> list = articleDomainService.listByStatus(commonQuery.getPage(), commonQuery.getSize(), commonQuery.getKeyword(), status);
        List<ArticleDTO> list1 = articleToArticleDTOMapping.sourceToTarget(list);
        Long count = articleDomainService.countByStatus(commonQuery.getPage(), commonQuery.getSize(), commonQuery.getKeyword(), status);
        return new PageDTO<>(list1, count, commonQuery);
    }

    @Override
    public ArticleDTO get(Long id) {
        return articleToArticleDTOMapping.sourceToTarget(articleDomainService.get(id));
    }

    @Override
    public boolean delete(Long id) {
        return articleDomainService.delete(id);
    }

    @Override
    public PageDTO<ArticleDTO, CommonQuery> list(CommonQuery commonQuery, String userId) {
        List<Article> list = articleDomainService.list(commonQuery.getPage(), commonQuery.getSize(), commonQuery.getKeyword(), userId);
        List<ArticleDTO> list1 = articleToArticleDTOMapping.sourceToTarget(list);
        Long count = articleDomainService.count(commonQuery.getPage(), commonQuery.getSize(), commonQuery.getKeyword(), userId);
        return new PageDTO<>(list1, count, commonQuery);
    }
}
