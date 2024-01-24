package cc.cloudcoding.forum.domain.service.factory;

import cc.cloudcoding.dictionary.rpc.DictionaryService;
import cc.cloudcoding.forum.domain.service.ArticleDomainService;
import cc.cloudcoding.forum.domain.service.Topic;
import cc.cloudcoding.forum.model.po.TopicPO;
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
 * @time: 2023/10/29 10:15
 */

@Component
public class TopicFactory {
    @Autowired
    @Qualifier("snowflake")
    private IDGenerator idGenerator;

    @DubboReference(group = "dictionary")
    private DictionaryService dictionaryService;

    @Autowired
    private ArticleDomainService articleDomainService;

    public Topic createTopic(TopicPO po) {
        Topic topic = new Topic();
        topic.setId(String.valueOf(po.getId()));
        topic.setUserId(String.valueOf(po.getUserId()));
        topic.setName(po.getName());
        topic.setDescription(po.getDescription());
        topic.setLabel(po.getLabel());
        topic.setType(dictionaryService.getDictionaryById(Long.valueOf(po.getType())).getLabel());
        topic.setArticles(articleDomainService.getByTopicId(String.valueOf(po.getId())));
        return topic;
    }

    public List<Topic> createTopic(List<TopicPO> pos) {
        return pos.stream().map(this::createTopic).collect(Collectors.toList());
    }

    public TopicPO createTopicPO(Topic topic) {
        TopicPO po = new TopicPO();
        if (topic.getId() == null) {
            po.setId(Long.valueOf(idGenerator.nextID()));
        } else {
            po.setId(Long.valueOf(topic.getId()));
        }
        po.setUserId(Long.valueOf(topic.getUserId()));
        po.setName(topic.getName());
        po.setDescription(topic.getDescription());
        po.setLabel(topic.getLabel());
        po.setType(String.valueOf(dictionaryService.getDictionaryByLabel(topic.getType()).getId()));
        return po;
    }
}
