package cc.cloudcoding.forum.interfaces.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.forum.model.command.UpdateTopicCommand;
import cc.cloudcoding.forum.model.req.TopicReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TopicReqToUpdateTopicCommandMapping extends IMapping<TopicReq, UpdateTopicCommand> {
}
