package cc.cloudcoding.dictionary.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.dictionary.model.dto.GroupDTO;
import cc.cloudcoding.dictionary.model.res.GroupRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupDTOToResMapping extends IMapping<GroupDTO, GroupRes> {
}
