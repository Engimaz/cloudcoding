package cn.edu.hcnu.dictionary.application.service;

import cn.edu.hcnu.dictionary.model.dto.DictionaryDTO;
import cn.edu.hcnu.dictionary.model.query.DictionaryQuery;

public interface IDictionaryApplication {
    DictionaryDTO getDictionaryByLabel(String label);

    DictionaryDTO getDictionaryById(Long id);

    DictionaryDTO getDictionaryByValue(String value);
}
