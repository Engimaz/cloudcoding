package cc.cloudcoding.forum.application.service;

import cc.cloudcoding.base.model.CommonQuery;
import cc.cloudcoding.base.model.PageDTO;
import cc.cloudcoding.forum.model.command.AddTopicCommand;
import cc.cloudcoding.forum.model.command.UpdateTopicCommand;
import cc.cloudcoding.forum.model.dto.TopicDTO;

public interface ITopicApplication {
    TopicDTO add(AddTopicCommand addTopicCommand);

    TopicDTO update(UpdateTopicCommand updateTopicCommand);

    PageDTO<TopicDTO, CommonQuery> list(CommonQuery commonQuery);

    TopicDTO get(String id);

    PageDTO<TopicDTO, CommonQuery> list(CommonQuery commonQuery, String userId);

    boolean delete(String id, boolean delete);
}
