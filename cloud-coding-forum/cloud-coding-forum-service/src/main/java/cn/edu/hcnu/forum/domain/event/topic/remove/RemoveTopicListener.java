package cn.edu.hcnu.forum.domain.event.topic.remove;

import cn.edu.hcnu.forum.domain.service.ArticleDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 13:46
 */
@Service
public class RemoveTopicListener {

    @Autowired
    private ArticleDomainService articleDomainService;


    @EventListener
    public void handleCustomEvent(RemoveTopicEvent event) {
        if (event.isDeleteArticle()) {
            Optional.ofNullable(event.getTopicId()).ifPresent(topicId -> {
                articleDomainService.removeByTopicId(topicId);
            });
        } else {
            // 更新topic为null
            Optional.ofNullable(event.getTopicId()).ifPresent(topicId -> {
                articleDomainService.getByTopicId(topicId).forEach(article -> {
                    article.setTopicId("-1");
                    articleDomainService.update(article);
                });
            });
        }
    }
}
