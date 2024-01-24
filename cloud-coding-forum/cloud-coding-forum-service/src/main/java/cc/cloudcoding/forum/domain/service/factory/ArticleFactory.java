package cc.cloudcoding.forum.domain.service.factory;

import cc.cloudcoding.dictionary.rpc.DictionaryService;
import cc.cloudcoding.forum.domain.service.Article;
import cc.cloudcoding.forum.model.po.ArticlePO;
import cc.cloudcoding.id.domain.service.IDGenerator;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/15 20:06
 */

@Component
public class ArticleFactory {


    @Autowired
    @Qualifier("snowflake")
    private IDGenerator idGenerator;

    @DubboReference(group = "dictionary")
    private DictionaryService dictionaryService;


    public ArticlePO createArticlePO(Article article) {

        ArticlePO articlePO = new ArticlePO();

        if (article.getId() == null) {
            article.setId(Long.valueOf(idGenerator.nextID()));
        } else {
            articlePO.setId(article.getId());
        }
        articlePO.setTopicId(String.valueOf(article.getTopicId()));
        articlePO.setUserId(article.getUserId());
        articlePO.setTitle(article.getTitle());
        articlePO.setContent(article.getContent());
        articlePO.setStatus(dictionaryService.getDictionaryByLabel(article.getStatus()).getId());
        articlePO.setLabel(article.getLabel());
        articlePO.setAvatar(article.getAvatar());
        return articlePO;
    }

    public Article createArticle(ArticlePO articlePO) {
        Article article = new Article();
        article.setId(articlePO.getId());
        article.setTopicId(articlePO.getTopicId());
        article.setUserId(articlePO.getUserId());
        article.setAvatar(articlePO.getAvatar());
        article.setTitle(articlePO.getTitle());
        article.setContent(articlePO.getContent());
        article.setStatus(dictionaryService.getDictionaryById(articlePO.getStatus()).getLabel());
        article.setLabel(articlePO.getLabel());
        article.setCreateTime(articlePO.getCreateTime());
        article.setUpdateTime(articlePO.getUpdateTime());
        return article;
    }

    public List<ArticlePO> createArticlePO(List<Article> list) {
        return list.stream().map(this::createArticlePO).collect(Collectors.toList());
    }

    public List<Article> createArticle(List<ArticlePO> articlePOList) {
        return articlePOList.stream().map(this::createArticle).collect(Collectors.toList());
    }
}
