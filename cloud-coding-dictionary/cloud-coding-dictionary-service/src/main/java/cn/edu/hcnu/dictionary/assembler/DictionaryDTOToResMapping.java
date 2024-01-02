package cn.edu.hcnu.dictionary.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.dictionary.model.dto.DictionaryDTO;
import cn.edu.hcnu.dictionary.model.res.DictionaryRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface DictionaryDTOToResMapping extends IMapping<DictionaryDTO, DictionaryRes> {
}
