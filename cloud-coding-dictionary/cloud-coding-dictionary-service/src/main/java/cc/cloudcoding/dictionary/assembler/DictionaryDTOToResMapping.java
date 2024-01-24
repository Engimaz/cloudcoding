package cc.cloudcoding.dictionary.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.dictionary.model.dto.DictionaryDTO;
import cc.cloudcoding.dictionary.model.res.DictionaryRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface DictionaryDTOToResMapping extends IMapping<DictionaryDTO, DictionaryRes> {
}
