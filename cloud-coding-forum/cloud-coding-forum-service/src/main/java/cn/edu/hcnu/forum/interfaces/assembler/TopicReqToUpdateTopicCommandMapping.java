package cn.edu.hcnu.forum.interfaces.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.forum.model.command.UpdateTopicCommand;
import cn.edu.hcnu.forum.model.req.TopicReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TopicReqToUpdateTopicCommandMapping extends IMapping<TopicReq, UpdateTopicCommand> {
}
