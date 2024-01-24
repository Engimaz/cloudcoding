package cc.cloudcoding.forum.interfaces.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cn.edu.hcnu.forum.model.dto.TopicDTO;
import cn.edu.hcnu.forum.model.res.TopicRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TopicDTOToTopicResMapping extends IMapping<TopicDTO, TopicRes> {
}
