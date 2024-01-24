package cc.cloudcoding.forum.application.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cn.edu.hcnu.forum.domain.service.Topic;
import cn.edu.hcnu.forum.model.command.UpdateTopicCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateTopicCommandToTopicMapping extends IMapping<UpdateTopicCommand, Topic> {
}
