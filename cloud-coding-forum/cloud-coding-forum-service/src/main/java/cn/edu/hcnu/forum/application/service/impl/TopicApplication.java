package cn.edu.hcnu.forum.application.service.impl;

import cn.edu.hcnu.base.model.CommonQuery;
import cn.edu.hcnu.base.model.PageDTO;
import cn.edu.hcnu.forum.application.assembler.AddTopicCommandToTopicMapping;
import cn.edu.hcnu.forum.application.assembler.TopicToTopicDTOMapping;
import cn.edu.hcnu.forum.application.assembler.UpdateTopicCommandToTopicMapping;
import cn.edu.hcnu.forum.application.service.ITopicApplication;
import cn.edu.hcnu.forum.domain.service.ArticleDomainService;
import cn.edu.hcnu.forum.domain.service.Topic;
import cn.edu.hcnu.forum.domain.service.TopicDomainService;
import cn.edu.hcnu.forum.model.command.AddTopicCommand;
import cn.edu.hcnu.forum.model.command.UpdateTopicCommand;
import cn.edu.hcnu.forum.model.dto.TopicDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/29 9:53
 */
@Service
public class TopicApplication implements ITopicApplication {


    @Autowired
    private TopicDomainService topicDomainService;

    @Autowired
    private AddTopicCommandToTopicMapping addTopicCommandToTopicMapping;
    @Autowired
    private UpdateTopicCommandToTopicMapping updateTopicCommandToTopicMapping;
    @Autowired
    private TopicToTopicDTOMapping topicToTopicDTOMapping;

    @Autowired
    private ArticleDomainService articleDomainService;

    @Override
    public TopicDTO add(AddTopicCommand addTopicCommand) {
        Topic topic = addTopicCommandToTopicMapping.sourceToTarget(addTopicCommand);
        topic = topicDomainService.save(topic);
        return topicToTopicDTOMapping.sourceToTarget(topic);
    }

    @Override
    public TopicDTO update(UpdateTopicCommand updateTopicCommand) {
        Topic topic = updateTopicCommandToTopicMapping.sourceToTarget(updateTopicCommand);
        topic = topicDomainService.update(topic);
        return topicToTopicDTOMapping.sourceToTarget(topic);
    }

    @Override
    public PageDTO<TopicDTO, CommonQuery> list(CommonQuery commonQuery) {
        List<Topic> list = topicDomainService.list(commonQuery.getPage(), commonQuery.getSize(), commonQuery.getKeyword(), null);
        List<TopicDTO> list1 = topicToTopicDTOMapping.sourceToTarget(list);
        Long count = topicDomainService.count(commonQuery.getPage(), commonQuery.getSize(), commonQuery.getKeyword(), null);
        return new PageDTO<>(list1, count, commonQuery);
    }

    @Override
    public TopicDTO get(String id) {
        Topic topic = topicDomainService.queryById(id);
        return topicToTopicDTOMapping.sourceToTarget(topic);
    }

    @Override
    public PageDTO<TopicDTO, CommonQuery> list(CommonQuery commonQuery, String userId) {
        List<Topic> list = topicDomainService.list(commonQuery.getPage(), commonQuery.getSize(), commonQuery.getKeyword(), userId);
        List<TopicDTO> list1 = topicToTopicDTOMapping.sourceToTarget(list);
        Long count = topicDomainService.count(commonQuery.getPage(), commonQuery.getSize(), commonQuery.getKeyword(), userId);


        return new PageDTO<>(list1, count, commonQuery);
    }

    @Override
    public boolean delete(String id, boolean delete) {
        return topicDomainService.delete(id, delete);
    }
}
