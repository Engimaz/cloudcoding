package cc.cloudcoding.forum.domain.event.topic.add;

import cc.cloudcoding.forum.domain.service.Article;
import cc.cloudcoding.forum.domain.service.ArticleDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddTopicListener {

    @Autowired
    private ArticleDomainService articleDomainService;


    @EventListener
    public void handleCustomEvent(AddTopicEvent event) {

        // 更新文章与专栏之间的关系
        Optional.ofNullable(event.getTopic()).ifPresent(topic -> {
            Optional.ofNullable(topic.getArticles()).ifPresent(articles -> {
                articles.forEach(article -> {
                    Article article1 = articleDomainService.get(article.getId());
                    article1.setTopicId(topic.getId());
                    articleDomainService.update(article1);
                });
            });

        });


    }
}
