package cc.cloudcoding.forum.application.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.forum.domain.service.Topic;
import cc.cloudcoding.forum.model.command.AddTopicCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddTopicCommandToTopicMapping extends IMapping<AddTopicCommand, Topic> {
}
