package cc.cloudcoding.dictionary.application.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.dictionary.domain.service.dictionary.Dictionary;
import cc.cloudcoding.dictionary.model.dto.DictionaryDTO;
import org.mapstruct.Mapper;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/26 14:56
 */
@Mapper(componentModel = "spring")

public interface DictionaryMapping extends IMapping<Dictionary, DictionaryDTO> {
}
