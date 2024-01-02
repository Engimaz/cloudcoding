package cn.edu.hcnu.dictionary.assembler;


import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.dictionary.model.command.DictionaryCommand;
import cn.edu.hcnu.dictionary.model.dto.DictionaryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DictionaryDTOToCommandMapping extends IMapping<DictionaryDTO, DictionaryCommand> {
}
