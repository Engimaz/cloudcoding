package cc.cloudcoding.forum.domain.service;

import cn.edu.hcnu.forum.domain.event.topic.TopicPublisher;
import cn.edu.hcnu.forum.domain.service.factory.TopicFactory;
import cn.edu.hcnu.forum.infrastructure.repository.TopicRepository;
import cn.edu.hcnu.forum.model.po.TopicPO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/29 10:06
 */

@Service
public class TopicDomainService {

    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private TopicFactory topicFactory;

    @Autowired
    private TopicPublisher topicPublisher;

    public List<Topic> list(Integer page, Integer size, String keyword, String userId) {
        List<Topic> collect = this.pageQuery(page, size, keyword, userId).getRecords().stream().map((f) -> topicFactory.createTopic(f)).collect(Collectors.toList());
        return collect;
    }

    public Long count(Integer page, Integer size, String keyword, String userId) {
        return this.pageQuery(page, size, keyword, userId).getTotal();
    }

    private Page<TopicPO> pageQuery(Integer page, Integer size, String keyword, String userId) {
        Page<TopicPO> p1 = new Page<>(page, size);
        LambdaQueryWrapper<TopicPO> wrapper = new LambdaQueryWrapper<TopicPO>().like(keyword != null, TopicPO::getName, keyword).eq(userId != null, TopicPO::getUserId, userId);
        return topicRepository.getBaseMapper().selectPage(p1, wrapper);
    }

    public Topic queryById(String id) {
        return topicFactory.createTopic(topicRepository.getById(id));
    }

    public Topic update(Topic topic) {
        TopicPO topicPO = topicFactory.createTopicPO(topic);
        boolean b = topicRepository.updateById(topicPO);
        if (b) {
            // 发布更新事件
            topicPublisher.publishUpdateTopicEvent(topic);
            return topic;
        }
        return null;
    }

    public Topic save(Topic topic) {
        TopicPO topicPO = topicFactory.createTopicPO(topic);
        boolean b = topicRepository.save(topicPO);
        if (b) {
            // 发布创建专栏事件
            topic.setId(String.valueOf(topicPO.getId()));
            topicPublisher.publishAddTopicEvent(topic);
            return topic;
        }
        return null;
    }

    public boolean delete(String id, boolean delete) {
        boolean b = topicRepository.removeById(id);
        if (b) {
            topicPublisher.publishRemoveTopicEvent(id, delete);
        }
        return b;
    }
}
