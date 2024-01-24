package cc.cloudcoding.dictionary.assembler;


import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.dictionary.model.dto.DictionaryDTO;
import cc.cloudcoding.dictionary.model.command.DictionaryCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DictionaryDTOToCommandMapping extends IMapping<DictionaryDTO, DictionaryCommand> {
}
