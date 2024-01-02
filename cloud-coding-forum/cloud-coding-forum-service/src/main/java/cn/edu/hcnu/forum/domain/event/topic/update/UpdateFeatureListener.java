package cn.edu.hcnu.forum.domain.event.topic.update;

import cn.edu.hcnu.forum.domain.service.Article;
import cn.edu.hcnu.forum.domain.service.ArticleDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateFeatureListener {

    @Autowired
    private ArticleDomainService featureUrlDomainService;

    @EventListener
    public void handleCustomEvent(UpdateTopicEvent event) {

        // 移除之前的专栏文章
        Optional.ofNullable(event.getTopic()).ifPresent(topic -> {
            topic.getArticles().forEach(article -> {
                Article article1 = featureUrlDomainService.get(article.getId());
                article1.setTopicId("-1");
                featureUrlDomainService.update(article1);
            });
        });
        // 添加新的专栏文章
        Optional.ofNullable(event.getTopic()).ifPresent(topic -> {
            topic.getArticles().forEach(article -> {
                Article article1 = featureUrlDomainService.get(article.getId());
                article1.setTopicId(topic.getId());
                featureUrlDomainService.update(article1);
            });
        });
    }
}
