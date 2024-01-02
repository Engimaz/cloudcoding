package cn.edu.hcnu.dictionary.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.dictionary.model.dto.GroupDTO;
import cn.edu.hcnu.dictionary.model.res.GroupRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface GroupDTOToResMapping extends IMapping<GroupDTO, GroupRes> {
}
