package cc.cloudcoding.dictionary.application.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.dictionary.domain.service.group.Group;
import cc.cloudcoding.dictionary.model.dto.GroupDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMapping extends IMapping<Group, GroupDTO> {

}
