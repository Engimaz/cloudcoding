package cn.edu.hcnu.forum.application.service.impl;

import cn.edu.hcnu.base.model.CommonQuery;
import cn.edu.hcnu.base.model.PageDTO;
import cn.edu.hcnu.forum.application.assembler.AddArticleCommandToArticleMapping;
import cn.edu.hcnu.forum.application.assembler.ArticleToArticleDTOMapping;
import cn.edu.hcnu.forum.application.assembler.UpdateArticleCommandToArticleMapping;
import cn.edu.hcnu.forum.application.service.IArticleApplication;
import cn.edu.hcnu.forum.domain.service.Article;
import cn.edu.hcnu.forum.domain.service.ArticleDomainService;
import cn.edu.hcnu.forum.model.command.AddArticleCommand;
import cn.edu.hcnu.forum.model.command.UpdateArticleCommand;
import cn.edu.hcnu.forum.model.dto.ArticleDTO;
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
