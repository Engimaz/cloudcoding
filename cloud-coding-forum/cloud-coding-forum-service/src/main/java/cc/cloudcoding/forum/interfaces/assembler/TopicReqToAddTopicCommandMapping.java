package cc.cloudcoding.forum.interfaces.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cn.edu.hcnu.forum.model.command.AddTopicCommand;
import cn.edu.hcnu.forum.model.req.TopicReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TopicReqToAddTopicCommandMapping extends IMapping<TopicReq, AddTopicCommand> {
}
