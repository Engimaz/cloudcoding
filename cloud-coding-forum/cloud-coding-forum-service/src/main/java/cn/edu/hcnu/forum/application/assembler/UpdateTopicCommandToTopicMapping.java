package cn.edu.hcnu.forum.application.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.forum.domain.service.Topic;
import cn.edu.hcnu.forum.model.command.UpdateTopicCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateTopicCommandToTopicMapping extends IMapping<UpdateTopicCommand, Topic> {
}
