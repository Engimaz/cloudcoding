package cn.edu.hcnu.dictionary.application.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.dictionary.domain.service.group.Group;
import cn.edu.hcnu.dictionary.model.command.AddGroupCommand;
import cn.edu.hcnu.dictionary.model.command.UpdateGroupCommand;
import cn.edu.hcnu.dictionary.model.dto.GroupDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMapping extends IMapping<Group, GroupDTO> {

}
