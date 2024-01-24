package cc.cloudcoding.forum.domain.service;

import cc.cloudcoding.dictionary.rpc.DictionaryService;
import cn.edu.hcnu.forum.domain.service.factory.ArticleFactory;
import cn.edu.hcnu.forum.infrastructure.repository.ArticleRepository;
import cn.edu.hcnu.forum.model.po.ArticlePO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/15 20:05
 */

@Service
public class ArticleDomainService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleFactory articleFactory;

    public Article save(Article article) {
        ArticlePO articlePO = articleFactory.createArticlePO(article);
        boolean save = articleRepository.save(articlePO);
        if (save) {
            return articleFactory.createArticle(articlePO);
        }
        return null;
    }

    public Article update(Article article) {
        ArticlePO articlePO = articleFactory.createArticlePO(article);
        boolean save = articleRepository.updateById(articlePO);
        if (save) {
            return articleFactory.createArticle(articlePO);
        }
        return null;
    }

    public List<Article> list(Integer page, Integer size, String keyword, String userId) {
        List<Article> collect = this.pageQuery(page, size, keyword, userId).getRecords().stream().map((f) -> articleFactory.createArticle(f)).collect(Collectors.toList());
        return collect;
    }

    public Long count(Integer page, Integer size, String keyword, String userId) {
        return this.pageQuery(page, size, keyword, userId).getTotal();

    }


    private Page<ArticlePO> pageQuery(Integer page, Integer size, String keyword, String userId) {
        Page<ArticlePO> p1 = new Page<>(page, size);
        LambdaQueryWrapper<ArticlePO> wrapper = new LambdaQueryWrapper<ArticlePO>().like(keyword != null, ArticlePO::getContent, keyword).eq(userId != null, ArticlePO::getUserId, userId);
        return articleRepository.getBaseMapper().selectPage(p1, wrapper);
    }

    public Article get(Long id) {
        return articleFactory.createArticle(articleRepository.getById(id));
    }

    public boolean delete(Long id) {
        return articleRepository.removeById(id);
    }

    public void removeByTopicId(String topicId) {
        articleRepository.remove(new LambdaQueryWrapper<ArticlePO>().eq(ArticlePO::getTopicId, topicId));
    }

    public List<Article> getByTopicId(String topicId) {
        return articleFactory.createArticle(articleRepository.list(new LambdaQueryWrapper<ArticlePO>().eq(ArticlePO::getTopicId, topicId)));
    }

    public List<Article> listByStatus(Integer page, Integer size, String keyword, String status) {
        List<Article> collect = this.pageQueryStatus(page, size, keyword, status).getRecords().stream().map((f) -> articleFactory.createArticle(f)).collect(Collectors.toList());
        return collect;
    }

    @DubboReference(group = "dictionary")
    private DictionaryService dictionaryService;

    private Page<ArticlePO> pageQueryStatus(Integer page, Integer size, String keyword, String status) {
        Page<ArticlePO> p1 = new Page<>(page, size);
        LambdaQueryWrapper<ArticlePO> wrapper = new LambdaQueryWrapper<ArticlePO>().like(keyword != null, ArticlePO::getContent, keyword).eq(status != null, ArticlePO::getStatus, dictionaryService.getDictionaryByLabel(status).getId());
        return articleRepository.getBaseMapper().selectPage(p1, wrapper);
    }

    public Long countByStatus(Integer page, Integer size, String keyword, String status) {
        return this.pageQueryStatus(page, size, keyword, status).getTotal();

    }
}
