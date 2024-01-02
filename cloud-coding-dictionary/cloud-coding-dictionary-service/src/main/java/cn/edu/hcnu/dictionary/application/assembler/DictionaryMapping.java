package cn.edu.hcnu.dictionary.application.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.dictionary.domain.service.dictionary.Dictionary;
import cn.edu.hcnu.dictionary.model.dto.DictionaryDTO;
import org.mapstruct.Mapper;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/26 14:56
 */
@Mapper(componentModel = "spring")

public interface DictionaryMapping extends IMapping<Dictionary, DictionaryDTO> {
}
