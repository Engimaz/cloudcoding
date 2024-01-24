package cc.cloudcoding.dictionary.application.service;

import cc.cloudcoding.dictionary.model.dto.DictionaryDTO;

public interface IDictionaryApplication {
    DictionaryDTO getDictionaryByLabel(String label);

    DictionaryDTO getDictionaryById(Long id);

    DictionaryDTO getDictionaryByValue(String value);
}
